#####################################################################
# @author:      yangsx
# @date:        2022/10/21
# @params:      FromUrl      目标要迁移的nexus地址
#               FromRepo     目标要迁移的nexus的私服仓库
#               TargetRepo   迁移到的目标nexus私服仓库
#               TempDir      临时路径，用于存放jar和pom
# @description: 迁移nexus某个私服仓库的所有maven jar到一个新的nexus私服中
#####################################################################

# API文档：https://help.sonatype.com/repomanager3/integrations/rest-and-integration-api/components-api

param($FromUrl, $FromRepo, $TempDir)

$PSDefaultParameterValues['Out-File:Encoding'] = 'utf8'

$TempDirProject = "${TempDir}\project"
$PomPath = "${TempDir}\project\pom.xml"
$TempDirRepo = "${TempDir}\repo"


mkdir -Force $TempDir
Remove-Item -Path $TempDirProject -Recurse
mkdir -Force $TempDirProject
mkdir -Force $TempDirRepo

function generatePomDependencies($ContinuationToken)
{

    $Headers = @{
        Accept = "application/json"
    }
    $URL = "${FromUrl}/service/rest/v1/components?repository=$FromRepo"
    if ($ContinuationToken -eq $null)
    {
        return
    }

    if ($ContinuationToken -ne '') {
        $URL += "&continuationToken=$ContinuationToken"
    }

    $R = Invoke-RestMethod -Method GET -Headers $Headers -Uri $URL

    if ($R.items.length -eq 0)
    {
        return
    }

    foreach ($attrifact in $R.items)
    {
        $groupId = $attrifact.group
        $artifactId = $attrifact.name
        $version = $attrifact.version
        Write-Output "process jar  --> $groupId $artifactId $version"
        foreach ($asset in $attrifact.assets)
        {
            $dir = $TempDirRepo + '/' + [System.IO.Path]::GetDirectoryName($asset.path)
            $path = $TempDirRepo + '/' + $asset.path
            mkdir -Force $dir
            $url = $asset.downloadUrl
            Write-Output "download file $url to $path"
            Invoke-WebRequest -Uri $url -OutFile $path
        }

    }
    generatePomDependencies -ContinuationToken:$R.continuationToken
}

generatePomDependencies ''
