FROM openjdk:8-jre

MAINTAINER kevin <17615195790@163.com>

ENV APP_VERSION 0.0.1-SNAPSHOT

RUN mkdir /app

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

COPY provider-redis-$APP_VERSION.jar /app/app.jar
