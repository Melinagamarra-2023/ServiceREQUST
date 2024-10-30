FROM openjdk:17
LABEL authors="TKevin"
EXPOSE 8080
ADD target/springboot-images-servicerequest.jar springboot-images-servicerequest.jar
ENTRYPOINT ["java","-jar","/springboot-images-servicerequest.jar"]
