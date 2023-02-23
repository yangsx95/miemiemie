# environment-manifest

环境清单与部署脚本。

[TOC]

## 内网k8s host集群

### 使用KubeKey离线部署Kubernetes

参考: <https://kubesphere.io/zh/docs/v3.3/installing-on-linux/introduction/air-gapped-installation/>

---

> 以下操作在外网中执行

准备KubeKey Command Line 程序:

```shell
export KKZONE=cn
curl -sfL https://get-kk.kubesphere.io | VERSION=v2.2.2 sh -
mv ./kk /usr/local/bin/
```

导出离线部署包：

```shell
mkdir -p /data/kubekey
cd /data/kubekey
# 拷贝 manifest.yaml 到 . 
kk artifact export -m manifest.yaml -o kubernetes-v1.22.10.tar.gz
```

上传包到内网机器 `/data/kubeky`中，需要通过 split 命令切割上传：

```shell
# 分割
split -b 500M /data/kubeky/kubernetes-v1.22.10.tar.gz

# 上传 省略

# 进入内网机器，合并恢复文件
mkdir /data/kubeky/
cat xaa xab xac xad > /data/kubekey/kubernetes-v1.22.10.tar.gz
```

部署内网镜像仓库(这里不使用harbor是因为只有三台机器，完整的镜像仓库不适合与k8s部署在同一机器中)：

```shell
kk init registry -f ~/environment-manifest/manifest/host/kubenetes/config.yaml -a /data/kubekey/kubernetes-v1.22.10.tar.gz
```

修改docker register的端口，将其变为5000，防止后续与ingress发生端口冲突：

```shell
vi /etc/kubekey/registry/config.yaml
# 将端口443改为5000
sed "s/443/5000/g" /etc/kubekey/registry/config.yaml  -i
mv dockerhub.kubekey.local:5000 dockerhub.kubekey.local:5000:5000
# 重启服务
systemctl restart registry
```

推送k8s基础镜像到镜像仓库：

```shell
kk artifact image push -f ~/environment-manifest/manifest/host/kubenetes/config.yaml -a /data/kubekey/kubernetes-v1.22.10.tar.gz
```

准备部署k8s：

```shell
ansible-playbook -i ~/environment-manifest/ansible/k8s-host/inventory.ini ~/environment-manifest/ansible/k8s-host/prepare-install-k8s.yaml
```

部署k8s：

```shell
kk create cluster -f ~/environment-manifest/manifest/host/kubenetes/config.yaml -a /data/kubekey/kubernetes-v1.22.10.tar.gz --with-packages
```

部署完毕后，取消master的不可部署应用的污点：

```shell
# 去除master的污点，让master也可以部署非系统pod
kubectl taint nodes --all node-role.kubernetes.io/master-
```

### 配置kubelet

配置kubelet：

```shell
ansible-playbook -i ~/environment-manifest/ansible/k8s-host/inventory.ini ~/environment-manifest/ansible/k8s-host/kubelet.yaml
```

> 当前生产环境机器内存分为两个阶段的预警：
> 
> 1. 当前内存占用大于百分之八十五
> 2. 当前内存占用大于百分之九十


### 部署NFS Server以及StorageClass

部署nfs客户端、服务端，并启动nfs服务端：

```shell
ansible-playbook -i ~/environment-manifest/ansible/k8s-host/inventory.ini ~/environment-manifest/ansible/k8s-host/nfs.yaml
```

> 离线导入镜像到私服请参考： [镜像导入](shell/images-import/README.md)

导入 `nfs-subdir-external-provisioner/images-list.txt` 中的所有镜像到Registry中，并执行如下命令部署动态存储：

```shell
# helm部署
helm install nfs-subdir-external-provisioner /root/environment-manifest/manifest/host/nfs-subdir-external-provisioner/nfs-subdir-external-provisioner-4.0.17.tgz \
  --namespace=nfs-provisioner \
  --create-namespace \
  --set image.repository=dockerhub.kubekey.local:5000:5000/kubesphere/nfs-subdir-external-provisioner \
  --set image.tag=v4.0.2 \
  --set replicaCount=2 \
  --set storageClass.name=nfs-client \
  --set storageClass.defaultClass=true \
  --set nfs.server=192.168.2.24 \
  --set nfs.path=/ifs/kubernetes
```

### 离线部署KubeSphere

> 离线导入镜像到私服请参考： [镜像导入](shell/images-import/README.md)

导入kubesphere/images-list.txt中的镜像到harbor私服，并部署kubesphere：

```shell
kubectl apply -f /root/environment-manifest/manifest/host/kubesphere/kubesphere-installer.yaml
kubectl apply -f /root/environment-manifest/manifest/host/kubesphere/cluster-configuration.yaml

# 查询安装日志
kubectl logs -n kubesphere-system $(kubectl get pod -n kubesphere-system -l 'app in (ks-install, ks-installer)' -o jsonpath='{.items[0].metadata.name}') -f
```

等待安装完成，访问 <http://192.168.11.221:30880>，在登录之前，修改admin账户的密码：

```shell
kubectl patch users admin -p '{"spec":{"password":"Rhdk@2022"}}' --type='merge' && kubectl annotate users admin iam.kubesphere.io/password-encrypted-
```

### 离线部署SonarQube

> [官方建议配置](https://docs.sonarqube.org/latest/requirements/requirements/)

官方镜像对当前生产环境的虚拟化存在不兼容，需要修改Dockerfile添加相关依赖包，并重新生成镜像：

```shell
docker build . -t dockerhub.kubekey.local:5000/library/sonarqube:9.4.0-community-f8
docker push dockerhub.kubekey.local:5000/library/sonarqube:9.4.0-community-f8
```

> 备注： 搞了两三天😭，测试环境多个版本的数据库无论如何配置都连接不上（已放弃）。当前生产环境问题，参考 <https://github.com/SonarSource/docker-sonarqube/issues/544> 解决。

安装sonarqube:

```shell
helm upgrade --install sonarqube /root/environment-manifest/manifest/host/sonarqube/sonarqube-6.7.0.tgz \
  --namespace=kubesphere-devops-system \
  --set image.repository=dockerhub.kubekey.local:5000/library/sonarqube \
  --set image.tag=9.4.0-community-f8 \
  --set plugins.initContainerImage=dockerhub.kubekey.local:5000/rjkernick/alpine-wget:latest \
  --set plugins.initSysctlContainerImage=dockerhub.kubekey.local:5000/library/busybox:1.31 \
  --set plugins.initCertsContainerImage=dockerhub.kubekey.local:5000/adoptopenjdk/openjdk11:alpine \
  --set plugins.initTestContainerImage=dockerhub.kubekey.local:5000/dduportal/bats:0.4.0 \
  --set postgresql.global.imageRegistry=dockerhub.kubekey.local:5000 \
  --set service.type=NodePort \
  --set persistence.storageClass=nfs-client \
  --set resources.limits.memory=3Gi \
  --set resources.requests.memory=2Gi \
  --set plugins.install[0]=http://nexus.cpirhzl.com/repository/sonarqube-plugins/sonarqube-9.4.0-community/sonar-l10n-zh-plugin-9.4.jar \
  --set plugins.install[1]=http://nexus.cpirhzl.com/repository/sonarqube-plugins/sonarqube-9.4.0-community/sonar-dependency-check-plugin-3.0.1.jar \
  --set plugins.install[2]=http://nexus.cpirhzl.com/repository/sonarqube-plugins/sonarqube-9.4.0-community/sonarqube-community-branch-plugin-1.12.0.jar
```

部署完毕后使用如下命令获取控制台地址：

```shell
export NODE_PORT=$(kubectl get --namespace sonarqube -o jsonpath="{.spec.ports[0].nodePort}" services sonarqube-sonarqube)
export NODE_IP=$(kubectl get nodes --namespace sonarqube -o jsonpath="{.items[0].status.addresses[0].address}")
echo http://$NODE_IP:$NODE_PORT
```

然后访问 `http://$NODE_IP:$NODE_PORT/setup` 进安装配置即可。 

流水线相关配置参考：<https://kubesphere.com.cn/docs/v3.3/devops-user-guide/how-to-integrate/sonarqube/#%E5%AE%89%E8%A3%85-sonarqube-%E6%9C%8D%E5%8A%A1%E5%99%A8>

参考文档：<https://kubesphere.com.cn/forum/d/3384-kubespheredevopsdotnet-core>

### 离线部署Nexus3

> [官方建议配置](https://help.sonatype.com/repomanager3/product-information/system-requirements)

导入 `images-list.txt` 中的镜像，并给master3设置标签，让nexus固定在master3上：

```shell
kubectl label node master3 nexus=nexus
```

部署nexus3：

```shell
helm upgrade --install nexus /root/environment-manifest/manifest/host/nexus/nexus-repository-manager-42.0.1.tgz \
  --namespace=nexus3 \
  --create-namespace \
  --set image.repository=dockerhub.kubekey.local:5000/sonatype/nexus3 \
  --set image.tag=3.42.0 \
  --set persistence.storageClass=nfs-client \
  --set persistence.storageSize=15Gi \
  --set service.type=NodePort \
  --set nexus.resources.requests.memory=2.7Gi \
  --set nexus.resources.limits.memory=3Gi \
  --set nexus.nodeSelector.nexus=nexus 
```

迁移nexus的jar包，请参考：[nexus迁移jar](./shell/nexus/README.md)

流水线使用nexus，请参考：[在流水线中使用 Nexus](https://kubesphere.io/zh/docs/v3.3/devops-user-guide/examples/use-nexus-in-pipelines/)

### 暴露服务

首先启用集群网关：集群设置 -> 网关设置 ，启用后会自动生成一个项目网关 kubesphere-router-kubesphere-system。

修改NodePort的端口范围，每台机器都要操作：

```shell
vim /etc/kubernetes/manifests/kube-apiserver.yaml
# 增加
- --service-node-port-range=1-65535
```

修改ingress-controller NodePort的端口，目标在服务kubesphere-router-kubesphere-system中：

```yaml
spec:
  ports:
    - name: http
      protocol: TCP
      appProtocol: http
      port: 80
      targetPort: http
      nodePort: 80
    - name: https
      protocol: TCP
      appProtocol: https
      port: 443
      targetPort: https
      nodePort: 443
```

最后在 应用负载 -> 应用路由中增加对应的规则，并配置hosts，即可通过 80 / 443 访问对应的服务。
