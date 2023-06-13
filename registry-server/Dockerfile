FROM public.ecr.aws/i8h3f3r5/alpine:latest

RUN apk update && \
    apk upgrade
RUN apk add openjdk8-jre

COPY target/registry-0.0.1-SNAPSHOT.jar registry.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/registry.jar"]