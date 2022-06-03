#Data analytics

Data assembly and analysis. Generation of recommendations based on the processed data.

## Technology stack:
- Java 11
- Spring Boot
- Database (MySQL and H2)
- Spring Data JPA
- Bean Validation
- Logging SLF4j
- JUnit 5
- Spring Testing
- Maven
- Spring Security (JWT)
- Docker
- Docker Compose
- Mapper (MupStruct)
- Flyway
- Swagger
- Lombok

Board (trello.com): https://trello.com/b/e1uD5GdK/diplom-rest-tms-c51

## Docker (+ H2 database) developer version

Run the following commands to build and run the application:
- git clone https://github.com/gemuzkm/diplom-rest-c51.git
- cd diplom-rest-c51
- mvn verify
- docker build -f Dockerfile.h2 -t tms/diplom-rest-c51 .
- docker run -p 8080:8080 -d tms/diplom-rest-c51
- docker ps
- port 8080

## Docker-compose (+ MySQL8 database) developer version
Run the following commands to build and run the application:
- git clone https://github.com/gemuzkm/diplom-rest-c51.git
- cd diplom-rest-c51
- Maven - LifeCycle: package
- docker-compose build
- docker-compose up -d
- docker-compose ps
- Mysql access port: 3307, Web port: 8081

## Test users
### Role - USER
- Username: user
- Password: pass

### Role - ADMIN
- Username: admin
- Password: pass