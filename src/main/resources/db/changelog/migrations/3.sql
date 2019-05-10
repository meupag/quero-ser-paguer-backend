--liquibase formatted sql

--changeset cliente:3
CREATE TABLE `pedido` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `valor` decimal(10,2) DEFAULT NULL,
  `id_cliente` bigint(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9y4jnyp1hxqa386cnly0ay9uw` (`id_cliente`),
  CONSTRAINT `FK9y4jnyp1hxqa386cnly0ay9uw` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
