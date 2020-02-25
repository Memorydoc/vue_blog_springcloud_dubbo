FROM openjdk:8-jre

MAINTAINER kevin <17615195790@163.com>

ENV APP_VERSION 0.0.1-SNAPSHOT
ENV DOCKERIZE_VERSION v0.6.1
RUN wget http://www.sizegang.cn:8080/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

RUN mkdir /app

COPY provider-user-$APP_VERSION.jar /app/app.jar
ENTRYPOINT ["dockerize", "-timeout", "5m", "-wait", "tcp://ali.sizegang.cn:3306", "java",\
"-javaagent:/usr/local/skywalking/skywalking-agent.jar","-Dskywalking.agent.service_name=provider-user",\
"-Dskywalking.collector.backend_service=192.168.0.121:11800","-Xmx600m","-Djava.security.egd=file:/dev/./urandom","-jar", "/app/app.jar"]