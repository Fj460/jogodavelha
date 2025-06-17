# Dockerfile
FROM openjdk:17-jdk-slim

COPY ./target/Sports-App-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]