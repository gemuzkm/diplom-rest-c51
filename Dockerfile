FROM openjdk:11
LABEL maintainer="gemuzkm@gmail.com"
COPY /target/*.jar /app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]

