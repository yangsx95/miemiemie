#####################################################################
# @author:      yangsx
# @date:        2022/10/11
# @params:      ImageList 拉取镜像的清单文件
# @description: 使用docker批量拉取清单中的镜像文件
#               示例如下：
#               .\pull-docker-image.ps1 -ImageList:'.\image-list.txt'
#####################################################################
param($ImageList)

foreach($line in Get-Content "$ImageList") {
    if ($line -match '^\s*$') {
        Continue
    }
    if ($line -match '^#+') {
        Continue
    }
    docker pull $line
}