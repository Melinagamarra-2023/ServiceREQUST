FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/gestion_techservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_techservice.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_techservice.jar"]

