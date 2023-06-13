FROM public.ecr.aws/i8h3f3r5/alpine:latest

RUN apk update && \
    apk upgrade
RUN apk add openjdk8-jre

COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/user-service.jar"]