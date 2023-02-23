# Nexus相关脚本

## 迁移maven jar的脚本

下载jar： 

```shell
 .\maven-attifact-migration.ps1 -FromUrl:'http://192.168.11.15:8081' -FromRepo:3rdPartyMixed -TempDir:"E:\ttt" 
 ```

然后执行如下命令将jar推送到新仓库:

```shell
java -jar migrate-local-repo-tool.jar -cd "E:\ttt\repo" -t "http://nexus.cpirhzl.com/repository/3rdPartyMixed/"  -u admin -p Rhdk@2022 
```

## 迁移 11.15 的nexus仓库

```shell
 .\maven-attifact-migration.ps1 -FromUrl:'http://192.168.11.15:8081' -FromRepo:3rdPartyMixed -TempDir:"E:\3rdPartyMixed" 
java -jar migrate-local-repo-tool.jar -cd "E:\3rdPartyMixed\repo" -t "http://nexus.cpirhzl.com/repository/3rdPartyMixed/"  -u admin -p Rhdk@2022

.\maven-attifact-migration.ps1 -FromUrl:'http://192.168.11.15:8081' -FromRepo:maven-snapshots -TempDir:"E:\maven-snapshots"
java -jar migrate-local-repo-tool.jar -cd "E:\maven-snapshots\repo" -t "http://nexus.cpirhzl.com/repository/maven-snapshots/"  -u admin -p Rhdk@2022
 
.\maven-attifact-migration.ps1 -FromUrl:'http://192.168.11.15:8081' -FromRepo:maven-releases -TempDir:"E:\maven-releases"
java -jar migrate-local-repo-tool.jar -cd "E:\maven-releases\repo" -t "http://nexus.cpirhzl.com/repository/maven-releases/"  -u admin -p Rhdk@2022

```