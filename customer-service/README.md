### Architecture

The application was developed in  [Spring boot](https://projects.spring.io/spring-boot/)  
data is being saved using [Mongodb](https://www.mongodb.com), 
the tests were developed using version 5 of  [JUnit](http://junit.org/junit5/) 
and documentation for use of api's was used [Swagger](https://swagger.io/).

### Running application with docker
```
mvn clean package docker:build
```
```
docker-compose up
```