FROM openjdk:8-jre-alpine
MAINTAINER huangdl <307926177@qq.com>

ARG exposePort
ENV SPRING_PROFILES_ACTIVE=test \
    DUBBO_IP_TO_REGISTRY=116.62.24.252 \
    SPRING_CLOUD_NACOS_DISCOVERY_IP=116.62.24.252 \
    DUBBO_PORT_TO_REGISTRY=${exposePort} \
    # DUBBO_IP_TO_BIND=116.62.24.252 \
    DUBBO_PORT_TO_BIND=${exposePort} \
    TZ=Asia/Shanghai

EXPOSE ${exposePort}
VOLUME /tmp
ADD target/xinggou-rc-service-0.0.1-SNAPSHOT.jar /service.jar
ENTRYPOINT ["java","-jar","/service.jar"]
