FROM openjdk:17
LABEL authors="TKevin"
EXPOSE 8181
ADD target/springboot-images-servicerequest.jar springboot-images-servicerequest.jar
ENTRYPOINT ["java","-jar","/springboot-images-servicerequest.jar"]
