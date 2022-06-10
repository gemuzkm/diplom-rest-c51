<h2>Table of Contents</h2>
<ol>
    <li><a href="#about">About the projectn</a></li>
    <li><a href="#stack">Technology stack</a></li>
    <li><a href="#board">Board (trello.com)</a></li>
    <li><a href="#docker_h2">Docker (+ H2 database) developer version</a></li>
    <li><a href="#port_service_docker">Ports & Services (Docker (+ H2 database) developer version)</a></li>
    <li><a href="#docker_mysql">Docker-compose (+ MySQL8 database) developer version</a></li>
    <li><a href="#port_service_docker_compose">Ports & Services (Docker-compose (+ MySQL8 database)</a></li>
    <li><a href="#test_users">Test users</a></li>
</ol>

<h2 id="about">About the project</h2>
<p>
    Data assembly and analysis. Generation of recommendations based on the processed data.
</p>

<h2 id="stack">Technology stack:</h2>
<ul>
    <li>
        Java 11
    </li>
    <li>
        Spring Boot
    </li>
    <li>
        Database (MySQL and H2)
    </li>
    <li>
        Spring Data JPA
    </li>
    <li>
        Bean Validation
    </li>
    <li>
        Logging SLF4j
    </li>
    <li>
        JUnit 5
    </li>
    <li>
        Spring Testing
    </li>
    <li>
        Maven
    </li>
    <li>
        Spring Security (JWT)
    </li>
    <li>
        Docker
    </li>
    <li>
        Docker Compose
    </li>
    <li>
        Mapper (MupStruct)
    </li>
    <li>
        Flyway
    </li>
    <li>
        Swagger
    </li>
    <li>
        Lombok
    </li>
</ul>

<h2 id="board">Board (trello.com)</h2>

<p>
    Link: <a href="https://trello.com/b/e1uD5GdK/diplom-rest-tms-c51">https://trello.com/b/e1uD5GdK/diplom-rest-tms-c51</a> 
</p>

<h2 id="docker_h2">Docker (+ H2 database) developer version</h2>
<p>
    Run the following commands to build and run the application:
</p>

````bash
git clone https://github.com/gemuzkm/diplom-rest-c51.git
````

````bash
cd diplom-rest-c51
````

````bash
mvn package
````

````bash
docker build -f Dockerfile.h2 -t tms/diplom-rest-c51 .
````

````bash
docker run -p 8080:8080 -d tms/diplom-rest-c51
````

````bash
docker ps
````

<h2 id="port_service_docker">Ports & Services (Docker (+ H2 database) developer version)</h2>

<ul>
    <li>
       Web port: <code>8080</code>
    </li>    
    <li>
        Swagger URL: <code>{your_host_url}/swagger-ui/</code>
    </li>
    <li>
        H2: <code>{your_host_url}/db/</code>
    </li>
    <li>
        H2 JDBC URL: <code>jdbc:h2:mem:db</code>
    </li>
    <li>
        H2 User Name: <code>sa</code>
    </li>
    <li>
        H2 Password: <code>{empty}</code> (login without password)
    </li>
</ul>


<h2 id="docker_mysql">Docker-compose (+ MySQL8 database) developer version</h2>
<p>
    Run the following commands to build and run the application:
</p>

````bash
git clone https://github.com/gemuzkm/diplom-rest-c51.git
````

````bash
cd diplom-rest-c51
````

````bash
mvn package
````

````bash
docker-compose build
````

````bash
docker-compose up -d
````

````bash
docker-compose ps
````

<h2 id="port_service_docker_compose">Ports & Services (Docker-compose (+ MySQL8 database)</h2>
<ul>
    <li>
        Mysql access port: <code>3307</code>
    </li>
    <li>
       Web port: <code>8081</code>
    </li>
    <li>
        Swagger URL: <code>{your_host_url}/swagger-ui/</code>
    </li>
</ul>

<h2 id="test_users">Test users</h2>
<h3>Role - USER</h3>
<ul>
    <li>
        Username: <code>user</code>
    </li>
    <li>
       Password: <code>pass</code>
    </li>
</ul>

<h3>Role - ADMIN</h3>
<ul>
    <li>
        Username: <code>admin</code>
    </li>
    <li>
       Password: <code>pass</code>
    </li>
</ul>

<br />
<div align="center">
    <img src="https://forthebadge.com/images/badges/built-with-love.svg" />
    <img src="https://forthebadge.com/images/badges/built-by-developers.svg" />
</div>