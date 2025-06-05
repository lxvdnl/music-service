FROM openjdk:17-jdk-slim

COPY target/*.jar user-service.jar

ENTRYPOINT ["java", "-jar", "user-service.jar"]

EXPOSE 8080