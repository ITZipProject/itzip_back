# Dockerfile for Java 21
FROM openjdk:21-jre-slim

COPY build/libs/itzip-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
