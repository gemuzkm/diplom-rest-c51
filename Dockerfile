#docker build -t tms/diplom-rest-c51 .
#docker run -d -p 8080:8080 tms/diplom-rest-c51

FROM openjdk:11
COPY /target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
