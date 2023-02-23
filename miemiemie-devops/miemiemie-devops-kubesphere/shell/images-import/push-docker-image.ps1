#####################################################################
# @author:      yangsx
# @date:        2022/10/11
# @params:      ImageList 拉取镜像的清单文件
#               Registry  私服的域名
# @description: 使用docker批量推送清单中的镜像文件，前提是本地要存在此镜像
#               示例如下：
#               .\push-docker-imge.ps1 -ImageList:'.\image-list.txt' -Registry:'localhost'
#####################################################################

param($ImageList, $Registry)
$Registry="$Registry".Trim("/")

foreach($line in Get-Content "$ImageList") {
    if ($line -match '^\s*$') {
        Continue
    }
    if ($line -match '^#+') {
        Continue
    }

    # 如果镜像格式为 `{image_name}:{tag}`，则代表 docker.io/library 仓库下的镜像，需要添加library前缀
    if ($line -match '^[^/]+$') {
        $imageNameAndTag = (-join("$Registry", "/", "library", "/", $line))
    }
    # 如果镜像格式为 `{project_name}/{image_name}:{tag}`
    elseif($line -match '^[^/]+/[^/]+$') {
        $imageNameAndTag = (-join("$Registry", "/", $line))
    }
    # 如果镜像格式为 `{registry}/{project_name}/{image_name}:{tag}`，将其Registry直接改为目标地址
    elseif($line -match '^[^/]+/[^/]+/[^/]+$') {
        $parters = "$line".Split('/')
        $imageNameAndTag = (-join("$Registry", "/", $parters[1], "/", $parters[2]))
    }
    # 不认得的镜像格式
    else {
        Continue
    }
    docker tag $line $imageNameAndTag
    docker push $imageNameAndTag
}