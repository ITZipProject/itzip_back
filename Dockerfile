# Dockerfile for Java 21
FROM bellsoft/liberica-openjdk-alpine:21

COPY build/libs/itzip-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
