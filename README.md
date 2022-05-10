#Data analytics

Data assembly and analysis. Generation of recommendations based on the processed data.

## Technology stack:
- Java 11
- Spring 5
- Spring Boot
- Database (MySQL and H2)
- Spring Data JPA
- Bean Validation
- Logging SLF4j
- Junit
- Maven
- Spring Security (JWT)
- Docker Compose
- Mapper (MupStruct)
- Swagger

Board (trello.com): https://trello.com/b/e1uD5GdK/diplom-rest-tms-c51

## Docker

Run the following commands to build and run the application:
- Maven - LifeCycle: package 
- docker build -f Dockerfile.h2 -t tms/diplom-rest-c51 .
- docker run -p 8080:8080 -d tms/diplom-rest-c51
- docker ps
- port 8080

## Test users
### Role - USER
- Username: user
- Password: pass
 
### Role - ADMIN
- Username: admin
- Password: pass