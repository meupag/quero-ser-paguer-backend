FROM maven:3.6.1-jdk-11-slim

WORKDIR /app
COPY . /app

RUN mvn clean package
ENTRYPOINT exec java -jar target/pag-rest-service-0.1.0.jar

EXPOSE 8080