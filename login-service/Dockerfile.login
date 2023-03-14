FROM alpine

RUN apk update && \
    apk upgrade
RUN apk add openjdk8-jre

ARG JAR_FILE=target/auth-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]