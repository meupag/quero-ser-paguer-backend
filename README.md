# Pag! order

This API is responsible for maintaining clients's orders of Pag! products.

##  Technical features

* Configured actuators
* MySQL driver configuration
* Docker compose script for MySQL service image
* Liquibase for database migration
* Use of clean code and good OO principles
* i18n message bundles for API response messages
* Spring Cloud Sleuth and Zipkin client configured
* Log instrumented to show distributed tracing
* Log rotation configured to rotate daily
* Swagger2 API documentation

API's base URL http://localhost:9090/pag-order

## Service setup

* Cd into project's folder
* Start the MySQL service

`$ docker-compose up`

* Connect to the MySQL service and create the **order** database

```$ docker exec -it pagorder_db_1 bash```

where **pagorder_db_1** is the docker container name

* Start the MySQL client

```root@6032f081a4cb:/# mysql -uroot -ppassword```

* Create the **order** database

```mysql> CREATE DATABASE `order`;```

* Exit the MySQL session

* Apply the database migration

`$ mvn liquibase:update`

* Start the application

`$ mvn clean spring-boot:run`

* Verify that the application is UP accessing this URL in the browser

[API Health actuator](http://localhost:9090/pag-order/actuator/health)

with the result: `{"status":"UP"}`

## API documetation

[Pag! Order API](http://localhost:9090/pag-order/swagger-ui.html)

## Resources

* Postman collection on folder `postman`

## Logs

* The application logs are generated in folder `logs` and the archived logs are in folder `logs/archive`
