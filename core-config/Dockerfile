#打包需要用到的jdk
FROM openjdk:8-jdk-alpine
#时间地区校准
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#前面是idea打包的jar包路径，路径不能错，  后面是 添加到docker的jar包名称，可自定义
ADD /target/*.jar app.jar
#运行jar命令
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]