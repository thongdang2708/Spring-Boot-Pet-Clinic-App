# Pet-Clinic-App-with-Spring-Boot-Spring-Data-JPA-Spring-Security-MySQL
APIs for Pet Clinic App with Authentication and Authorisation based on Spring Boot + Spring Data JPA + Spring Security + MySQL

Warning: Due to high charge costs for AWS services, database is no longer hosted on AWS RDS. I built on Docker instead.

Image for Postman testing:

<img width="286" alt="image" src="https://user-images.githubusercontent.com/89829761/212301815-9c82e4ae-161e-47c8-b52c-de835637b0ef.png">
<img width="267" alt="image" src="https://user-images.githubusercontent.com/89829761/212301891-398ac189-eab8-4c51-b9cc-68ac606f7bd2.png">

Configuration with MySQL deployed to AWS:

<img width="811" alt="image" src="https://user-images.githubusercontent.com/89829761/212753607-a5ad5312-d03c-443e-920b-26fb8603ae77.png">


Security config on AWS is set open with all IPv4 addresses

In application.properties on GitHub, there are some hidden information for database connection:

spring.datasource.username=*****(Hidden for security)

spring.datasource.password=************(Hidden for security)

```bash
To start, use "mvn clean spring-boot:run"
```

or

```bash
docker-compose up
```

When stopping the app, close with the below command in backend directory:

```bash
docker-compose down
```

Checking the running containers, use the command in backend directory:

```bash
docker-compose ps
```


