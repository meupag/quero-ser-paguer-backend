--liquibase formatted sql

--changeset pag:1
CREATE TABLE IF NOT EXISTS  `cliente` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `cpf` varchar(11) DEFAULT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r1u8010d60num5vc8fp0q1j2a` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--changeset pag:2
CREATE TABLE IF NOT EXISTS `produto` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `preco_sugerido` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--changeset pag:3
CREATE TABLE IF NOT EXISTS `pedido` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `valor` decimal(10,2) DEFAULT NULL,
  `id_cliente` bigint(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9y4jnyp1hxqa386cnly0ay9uw` (`id_cliente`),
  CONSTRAINT `FK9y4jnyp1hxqa386cnly0ay9uw` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--changeset pag:4
CREATE TABLE IF NOT EXISTS `item_pedido` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `preco` decimal(10,2) NOT NULL,
  `quantidade` decimal(10,2) NOT NULL,
  `id_pedido` bigint(11) NOT NULL,
  `id_produto` bigint(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_r3ra9ncs6675cucdn31smm5he` (`id_produto`),
  KEY `FKnjghutiejefh2auj9bnpf9sp7` (`id_pedido`),
  CONSTRAINT `FKnjghutiejefh2auj9bnpf9sp7` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`),
  CONSTRAINT `FKnqncqw8pnv54kv0dct6mo2iei` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--changeset pag:5
REPLACE INTO `cliente` VALUES (4,'11111111111','1986-01-01 09:24:00','João da Silva'),(5,'11111111112','1986-01-01 09:24:00','Maria da Silva');

--changeset pag:6
REPLACE INTO `produto` VALUES (4,'Aguá',5.50),(5,'Pão de Queijo',6.50),(6,'Refrigerante',7.50);


--changeset pag:7
REPLACE INTO `pedido` VALUES (4,477.90,4),(5,477.90,4);

--changeset pag:8
REPLACE INTO `item_pedido` VALUES (6,15.23,30.00,4,6),(7,11.00,1.00,4,5),(8,10.00,1.00,4,4),(9,15.23,30.00,5,6),(10,11.00,1.00,5,5),(11,10.00,1.00,5,4);



