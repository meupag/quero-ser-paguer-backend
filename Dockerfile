FROM openjdk:8-jdk-alpine
VOLUME /tmp
WORKDIR /tmp
COPY . /tmp

ENTRYPOINT ./mvnw package && java -Djava.security.egd=file:/dev/./urandom -jar target/quero-ser-paguer-0.0.1-SNAPSHOT.jar
EXPOSE 8080