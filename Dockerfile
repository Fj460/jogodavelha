# Dockerfile

FROM openjdk:17
COPY ./target/Agentes-IA-0.0.1-SNAPSHOT.jar /tmp
COPY src /home/app/src
COPY pom.xml /home/app
COPY src/main/resources/application.properties /home/app/properties
ENTRYPOINT ["java", "-jar", "/tmp/Agentes-IA-0.0.1-SNAPSHOT.jar"]