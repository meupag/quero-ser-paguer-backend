FROM 965946547559.dkr.ecr.us-east-2.amazonaws.com/base-jdk-12


LABEL maintainer="Heitor Machado"

LABEL app.name="pag-web-api"
LABEL app.version="1.0.0"
	
EXPOSE 80 443

ADD target/pag-web-api-0.0.1-SNAPSHOT.jar /home/app/pag-web-api-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/home/app/pag-web-api-0.0.1-SNAPSHOT.jar"]