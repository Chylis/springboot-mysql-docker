# The image is based in a minimalistic (alpine) linux with JRE 8
FROM openjdk:8-jre-alpine
MAINTAINER "Magnus Eriksson" <magnus.g.eriksson@gmail.com>

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
