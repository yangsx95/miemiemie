# 离线导入镜像

离线导出相关镜像到harbor私服，共有两种方式：

## 使用 offline-installation-tool.sh

> 文件太大，上传太慢了，很吃磁盘资源
```shell
chmod +x offline-installation-tool.sh
./offline-installation-tool.sh -s -l images-list.txt -d ./kubesphere-images
```

拷贝脚本与文件夹kubesphere-images到目标内网服务器，并执行脚本进行离线导入：

```shell
./offline-installation-tool.sh -l images-list.txt -d ./kubesphere-images -r dockerhub.kubekey.local
```

## win下导入Docker Desktop到DACS，直接push

使用powershell脚本批量拉取目标镜像，以下操作再DACS外，且有外网的情况下执行：

```powershell
.\pull-docker-image.ps1 -ImageList:'.\image-list.txt'
```

在DACS内打开Docker Desktop，执行如下脚本将镜像推送到内网私服Harbor中：

```powershell
.\shell\images-import\push-docker-image.ps1 -ImageList:'.\manifest\host\kubesphere\images-list.txt' -Registry:'dockerhub.kubekey.local:5000'
```

> 注意push之前需要配置hosts文件，将ip和harbor域名映射，且将私服地址加入到docker daemon 非安全镜像仓库配置中：
> ```shell
> "insecure-registries": [
>     "dockerhub.kubekey.local:5000"
> ]
> ```

### 示例

以下操作Win x86下DACS外执行：

```powershell
.\images-import\pull-docker-image.ps1 -ImageList:'.\kubesphere\images-list.txt'
```

以下操作DACS内执行：

```powershell
# 创建镜像仓库
.\shell\images-import\create-harbor-projects.ps1 -ImageList:'.\manifest\host\kubesphere\images-list.txt' -HarborUrl:'https://dockerhub.kubekey.local' -HarborUser:'admin' -HarborPasswd:'Harbor12345'

# 导入镜像
.\shell\images-import\push-docker-image.ps1 -ImageList:'.\manifest\host\kubesphere\images-list.txt' -Registry:'dockerhub.kubekey.local'
```

> 注意，本地调用harbor api需要安装harbor证书
