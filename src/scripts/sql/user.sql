CREATE DATABASE pag;
CREATE USER 'pag'@'localhost' IDENTIFIED BY 'pag';
GRANT ALL ON pag.* TO 'pag'@'localhost';
exit;