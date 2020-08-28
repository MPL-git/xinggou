FROM openjdk:8-jre-alpine
MAINTAINER huangdl <307926177@qq.com>

ARG exposePort
ENV SPRING_PROFILES_ACTIVE=test \
    TZ=Asia/Shanghai

EXPOSE ${exposePort}
VOLUME /tmp
ADD target/xinggou-admin-api-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]