FROM openjdk:11.0.7-jre-slim-buster
MAINTAINER Andrey Fillipe
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} proposta-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "/proposta-0.0.1-SNAPSHOT.jar"]