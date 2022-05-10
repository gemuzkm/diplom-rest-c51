FROM openjdk:11
LABEL maintainer="gemuzkm@gmail.com"
WORKDIR /
COPY /target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]