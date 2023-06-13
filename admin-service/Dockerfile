FROM public.ecr.aws/i8h3f3r5/alpine:latest

RUN apk update && \
    apk upgrade
RUN apk add openjdk8-jre

COPY target/admin-service-0.0.1-SNAPSHOT.jar admin.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","/admin.jar"]