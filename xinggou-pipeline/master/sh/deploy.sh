#!/bin/bash
# -------------------------------------------------------------------------------
# Filename:    deploy.sh
# Description: 服务部署、备份、启动
# exp:./deploy
# -------------------------------------------------------------------------------

workspace=$1
appName=$2
imageName=$3
exposePort=$4
hostPort=$5
dockerContext=$6
dockerfile=$7
runParam="$8"

validateRes=0
function validate(){
    if [ -z "$workspace" ]; then
        echo "参数1:workspace不能为空"
        validateRes=1
    fi
    if [ -z "$appName" ]; then
        echo "参数2:appName不能为空"
        validateRes=1
    fi
    if [ -z "$imageName" ]; then
        echo "参数3:imageName不能为空"
        validateRes=1
    fi
    if [ -z "$exposePort" ]; then
        echo "参数4:exposePort不能为空"
        validateRes=1
    fi
    if [ -z "$hostPort" ]; then
        echo "参数5:hostPort不能为空"
        validateRes=1
    fi
    if [ -z "$dockerContext" ]; then
        echo "参数6:dockerContext不能为空"
        validateRes=1
    fi
    if [ -z "$dockerfile" ]; then
        echo "参数7:dockerfile不能为空"
        validateRes=1
    fi
}


# 删除容器
function delContainer(){
    CID=$(docker ps -a | grep "${appName}" | awk '{print $1}')
    if [ -n "$CID" ]; then
        echo "存在${appName}容器，CID=$CID,停止并删除docker容器 ..."
        docker rm -f $CID
        echo "${appName}容器停止删除完成"
    else
        echo "不存在${appName}容器"
    fi

}

# 构建docker镜像
function buildImage(){
    IID=$(docker images | grep "${appName}" | awk '{print $3}')
    if [[ -n "$IID" ]]; then
        echo "存在${appName}镜像，IID=$IID"
        echo "删除${appName}镜像..."
        docker rmi $IID
    else
        echo "不存在${appName}镜像"
    fi

    echo "开始构建镜像in:${dockerContext} -f ${dockerfile}"
    cd ${dockerContext}
    docker build \
        --build-arg exposePort=${exposePort} \
        -f ${dockerfile} \
        -t ${imageName} .
}



# 运行docker容器
function run(){
    validate
    if [[ ${validateRes} -eq 1 ]]; then
        echo "参数校验不通过"
        return;
    fi


    delContainer
    buildImage
    echo "启动容器"
    docker run -d ${runParam} \
        --name ${appName} \
        --restart always \
        -p ${hostPort}:${exposePort} \
        ${imageName}
}

#入口
run