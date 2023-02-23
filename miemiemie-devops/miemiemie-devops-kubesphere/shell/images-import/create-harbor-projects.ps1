#####################################################################
# @author:      yangsx
# @date:        2022/10/12
# @params:      ImageList 拉取镜像的清单文件
# @description: 解析镜像清单文件，创建仓库
# @example:     .\create-harbor-projects.ps1 -ImageList:'.\image-list.txt' -HarborUrl:'https://dockerhub.kubekey.local' -HarborUser:'admin' -HarborPasswd:'Harbor12345'
#####################################################################
param($ImageList, $HarborUrl, $HarborUser, $HarborPasswd)

$repoList = [System.Collections.ArrayList]::new()
foreach($line in Get-Content "$ImageList") {
    if ($line -match '^[^/]+$') {
        $repoList.Add("Library")
        Continue
    }
    $tmp = $line.split('/')
    if($line -match '^[^/]+/[^/]+$') {
        $repoList.Add($tmp[0]);
        Continue
    }
    if($line -match '^[^/]+/[^/]+/[^/]+$') {
        $repoList.Add($tmp[1]);
        Continue
    }

}
$repoList = $repoList | Sort-Object | Get-Unique

# 循环仓库并创建
$pair = "$($HarborUser):$($HarborPasswd)"
$encodedCredentials = [System.Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes($pair))
$Headers = @{
    Authorization = "Basic $encodedCredentials"
    "Content-Type" = "application/json"
}
foreach ($project in $repoList) {
    Invoke-WebRequest -Method Post -Headers $Headers -Uri "${HarborUrl}/api/v2.0/projects" -Body  "{ `"project_name`": `"${project}`", `"public`": true}"
}
