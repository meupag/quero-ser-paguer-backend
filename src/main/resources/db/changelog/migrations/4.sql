--liquibase formatted sql

--changeset cliente:4
CREATE TABLE `item_pedido` (
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
