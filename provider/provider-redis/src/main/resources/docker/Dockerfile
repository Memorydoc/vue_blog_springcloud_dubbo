FROM openjdk:8-jre

MAINTAINER kevin <17615195790@163.com>

ENV APP_VERSION 0.0.1-SNAPSHOT

RUN mkdir /app



COPY provider-redis-$APP_VERSION.jar /app/app.jar


ENTRYPOINT ["java","-javaagent:/usr/local/docker/skywalking/apache-skywalking-apm-bin/agent/skywalking-agent.jar","-Dskywalking.agent.service_name=provider-redis",\
"-Dskywalking.collector.backend_service=192.168.0.121:11800", "-Xmx600m","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]