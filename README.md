<h1 align="center">Shopping Cart</h1>

### Description

Shopping cart implemented from scratch using spring boot 2. This is a Rest API that allows you to create a Cart with items, delete Cart, add item into existing Cart, and retrieve Cart information. Also there is a Scheduler checking cart inactivity to clean those from the DB.

### Libraries used

1. Spring Boot 2.7.14
2. Spring Boot Test
3. Swagger API
4. JPA using Hibernate

### Features
These services can perform below operations, also this information can be found on Swagger documentation.

- Create Cart with Items POST
  if id field is null a new Cart would be created. otherwise would replace existing Cart.
```
  curl -X 'POST' \
  'http://localhost:8080/cart' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "string",
  "cartItems": [
    {
      "productId": 0,
      "quantity": 0
    }
  ]
}'
```
- Add item to existing Cart
```
curl -X 'POST' \
  'http://localhost:8080/cart/{cart-uuid}' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "productId": 1,
  "quantity": 10
}'
```
- Retrieve Cart information
```
curl -X 'GET' \
  'http://localhost:8080/cart/{cart-uuid}' \
  -H 'accept: */*'
```
- Delete Cart
```
curl -X 'DELETE' \
  'http://localhost:8080/cart/{cart-uuid}' \
  -H 'accept: */*'
```
- Retrieve all generated Products
```
curl -X 'GET' \
  'http://localhost:8080/product' \
  -H 'accept: */*'
```
### Project setup

This project use Maven 3 to handle dependencies and to build a runnable jar

At the root of this project you can execute:
```
mvn clean install
mvn spring-boot:run
```

### Database

This project uses H2 in memory database, every time the project is initiated the database would be created empty.
Once the project is running you can check the Database through the [console](http://localhost:8080/h2-console)
```
url=jdbc:h2:mem:cart
username=sa
no password
```

### Swagger documentation

You need to execute the application in order to open swagger link:
[swagger](http://localhost:8080/swagger-ui.html)

### Code coverage
JaCoCo plugin is used to check code coverage, you can execute:
```
mvn clean install
```
After that you can check code coverage report on:
*target/site/jacoco/index.html*

### Spring Boot Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.14/maven-plugin/reference/html/)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.7.14/reference/htmlsingle/index.html#appendix.configuration-metadata.annotation-processor)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.7.14/reference/htmlsingle/index.html#actuator)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/2.7.14/reference/htmlsingle/index.html#howto.data-access.exposing-spring-data-repositories-as-rest)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.14/reference/htmlsingle/index.html#web)
* [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/current/reference/html5/)

