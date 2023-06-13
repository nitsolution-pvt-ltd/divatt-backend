FROM public.ecr.aws/i8h3f3r5/alpine:latest

RUN apk update && \
    apk upgrade
RUN apk add openjdk8-jre

COPY target/designer-service-0.0.1-SNAPSHOT.jar designer-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/designer-service.jar"]