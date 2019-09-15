# Sample web application for credit limit calculating

A simple CRUD operation for user and auth mechanism application using Spring Boot with the following options:

- Spring JPA and H2DB for data persistence
- JWT auth token
- Authorization: Bearer system for authentication
- Rest Controller for requests
- Random.com random number generator api used for randomize credit point
- Feign used for connection with random.com
- Lombok used
- A Postman collection created, exported and added to resource folder. This collection can be import to Postman.



# Starting from console
* mvn spring-boot:run
* All endpoints need header Authorization: Bearer {{TOKEN}}, except /user/signup and /user/login
* Username: system password: system user created first. This user can be used to take token /user/login endpoint.
* A customer created from /customer/add endpoint. While creating, also credit limit will calculate.
* /customer/calculateCreditLimit/{customerId} endpoint can be used to calculate credit limit of already created customers


# Starting from Docker
    mvn install dockerfile:build
command create Docker image. This image can be added Docker. 




