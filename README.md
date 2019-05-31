# Pag! order

This API is responsible for maintaining clients's orders of Pag! products.

##  Technical features

* Configured actuators
* MySQL driver configuration
* Dockerfile
* Docker compose script for MySQL and Application service images
* Liquibase for database migration
* Use of clean code and good OO principles
* i18n message bundles for API response messages
* Spring Cloud Sleuth and Zipkin client configured
* Log instrumented to show distributed tracing
* Log rotation configured to rotate daily
* Swagger2 API documentation
* HATEOAS implementation

API's base URL http://localhost:9090/pag-order

## Building docker image

`$ docker build -t mroger/pag-order:0.0.1-SNAPSHOT .`

## Pag! Order Database Setup

* Cd into project's folder
* Start the MySQL db service

`$ docker-compose up db`

* Connect to the MySQL service and create the **order** database

```$ docker exec -it  pag-db bash```

where **pag-db** is the docker container name

* Start the MySQL client

```# mysql -uroot -ppassword```

* Create the **order** database

```mysql> CREATE DATABASE `order`;```

* Exit the MySQL and shell sessions

* Apply the database migration

`$ mvn liquibase:update`

* Stop the MySQL db service

`$ docker-compose down`

* Start the services

`$ docker-compose up`

* Verify that the application is UP accessing this URL in the browser

[API Health actuator](http://localhost:9090/pag-order/actuator/health)

with the result: `{"status":"UP"}`

## API documetation

[Pag! Order API](http://localhost:9090/pag-order/swagger-ui.html)

## Resources

* Postman collection on folder `postman`

## Logs

* The application logs are generated in folder `logs` and the archived logs are in folder `logs/archive`
