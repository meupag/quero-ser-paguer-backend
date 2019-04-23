CREATE DATABASE pag;
CREATE USER 'pag'@'%' IDENTIFIED BY 'pag';
GRANT ALL ON pag.* TO 'pag'@'%';