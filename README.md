
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


### Build/run

docker pull mysql
docker network create pag

docker run --name mysql --network pag -p 5010:3306 -e MYSQL_ROOT_PASSWORD=pag -d mysql

mysql -h 127.0.0.1 --port 5010 -u root -p < src/scripts/sql/user.sql
mysql -h 127.0.0.1 --port 5010 -u root -p < src/scripts/sql/data.sql

docker build .
docker run --name pag-service --network pag -p 8080:8080 [Docker image]