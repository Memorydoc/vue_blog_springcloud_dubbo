FROM openjdk:8-jre

MAINTAINER kevin <17615195790@163.com>

ENV APP_VERSION 0.0.1-SNAPSHOT

RUN mkdir /app

COPY service-elastic-job-$APP_VERSION.jar /app/app.jar

ENTRYPOINT ["java","-javaagent:/usr/local/skywalking/skywalking-agent.jar","-Dskywalking.agent.service_name=service-elastic-job",\
"-Dskywalking.collector.backend_service=192.168.0.121:11800", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

EXPOSE 9004