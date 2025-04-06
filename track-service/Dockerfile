FROM openjdk:17-jdk-slim

WORKDIR /track-service

COPY target/*.jar /track-service/track-service.jar

ENTRYPOINT ["java", "-jar", "/track-service/track-service.jar"]

EXPOSE 8081