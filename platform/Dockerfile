FROM openjdk:17-jdk-slim-buster
WORKDIR /app

COPY target/*.jar ./

ENTRYPOINT java -jar app.jar