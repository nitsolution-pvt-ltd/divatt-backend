FROM public.ecr.aws/i8h3f3r5/alpine:latest

RUN apk update && \
    apk upgrade
RUN apk add openjdk8-jre

COPY target/auth-0.0.1-SNAPSHOT.jar auth.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/auth.jar"]