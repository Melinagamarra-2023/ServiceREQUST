FROM openjdk:17
LABEL authors="TKevin"
EXPOSE 8080
ADD target/servicerequest-images.jar servicerequest-images.jar
ENTRYPOINT ["java","-jar","/servicerequest-images.jar"]
