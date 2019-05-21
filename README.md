
![](logo_pag.png)

### Você gosta de encarar desafios e quer fazer parte de uma equipe que não para de crescer? Então, venha para o time!
### Complete o desafio abaixo para a gente conhecer mais sobre você:

Faça o Fork deste projeto e desenvolva uma **API REST em Java** com o modelo de dados abaixo. 
Assim que concluir, faça um Pull Request e mande seu currículo para <queroserpaguer@meupag.com.br> com o assunto **quero-ser-paguer-backend**. Caso seja aprovado, iremos entrar em contato com você.

![](modelo-dados.png)

> **PS:** fique à vontade para incluir outros conhecimentos. Cada item do bônus que você adicionar ao seu projeto contará para a sua avaliação.

### Bônus
- Spring boot
- Clean Code
- Teste Unitário
- Controle de acesso
- API Documentada
- Cache de segundo nível
- Database Migration
- Bean Validation
- Docker

### Bônus Master (AWS)
- Lambda
- RDS / DynamoDB
- API Gateway
- Cognito
- [Serverless Framework](https://serverless.com/)

------------------------------------------------------------------------------

### Architecture

The application was developed in  [Spring boot](https://projects.spring.io/spring-boot/)  
data is being saved using [Mongodb](https://www.mongodb.com), 
the tests were developed using version 5 of  [JUnit](http://junit.org/junit5/) 
and documentation for use of api's was used [Swagger](https://swagger.io/).

### Running application with docker
```
In the customer-service and order-service directory execute
mvn clean package docker:build
```
```
In quero-ser-paguer-backend directory execute
docker-compose up
```

### Swaager
- customer service https://github.com/igormgomes/quero-ser-paguer-backend/blob/master/customer-service/src/main/resources/customer-swagger.yaml
- order service https://github.com/igormgomes/quero-ser-paguer-backend/blob/master/order-service/src/main/resources/order-swagger.yaml 
- https://editor.swagger.io