FROM openjdk:8-jre

MAINTAINER kevin <17615195790@163.com>

ENV APP_VERSION 0.0.1-SNAPSHOT

RUN mkdir /app

COPY cloud-sms-service-$APP_VERSION.jar /app/app.jar

ENTRYPOINT ["java","-javaagent:/usr/local/skywalking/skywalking-agent.jar","-Dskywalking.agent.service_name=cloud-sms-service",\
"-Dskywalking.collector.backend_service=192.168.0.121:11800", "-Xmx600m","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

EXPOSE 10003