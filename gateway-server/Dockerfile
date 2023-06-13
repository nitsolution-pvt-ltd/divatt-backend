FROM alpine

RUN apk update && \
    apk upgrade
RUN apk add openjdk8-jre

COPY target/gateway-0.0.1-SNAPSHOT.jar gateway.jar
EXPOSE 9095
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gateway.jar"]