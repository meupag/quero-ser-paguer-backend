### Build/run

docker pull mysql
docker network create pag

docker run --name mysql --network pag -p 5010:3306 -e MYSQL_ROOT_PASSWORD=pag -d mysql

mysql -h 127.0.0.1 --port 5010 -u root -p < src/scripts/sql/user.sql
mysql -h 127.0.0.1 --port 5010 -u root -p < src/scripts/sql/data.sql

docker build .
docker run --name pag-service --network pag -p 8080:8080 [Docker image]