#!/bin/sh

#声明
#参数1必填：项目名 例:core-config
#参数2非必填：是否强制用docker stack deploy 运行该程序，ps：如果有参数添加或修改compose文件，那就需要设置为true
deployName=$1
stackRun=${2:-false}
composePath=/opt/cloud/dockercompose/
if [ -z ${deployName} ];then
  echo 请输入要部署的项目
  exit
fi
echo 开始解析并部署${deployName}到生产环境中
array=(${deployName//-/ })
array_length=${#array[@]}
#检测项目名的合法性
if [ ${array_length} -eq 0 ];then
  echo 暂时不支持没有”-“符号的项目名，如果有项目名的特殊性，需要修改本脚本
  exit
fi

check_service=`docker service ls -f NAME=${array[0]}_${deployName} -q`
echo ${check_service}
if [[ ${check_service} == '' || 'true' == ${stackRun} ]]; then
  echo 尝试用compose文件启动
  composeFile=${composePath}"docker-compose-"${array[0]}".yml"
  echo composefile 为 ${composeFile}
  if [ ! -e ${composeFile} ]; then
    echo 启动失败，找不到compose文件，请检查或编排相关文件${composeFile}
    exit
  fi
  docker stack deploy -c ${composeFile} ${array[0]}
else
  echo 该服务已启动,正强制更新该服务（不更新配置文件）
  docker service update --force ${array[0]}_${deployName}
fi

echo 启动成功