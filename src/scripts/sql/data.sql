USE pag;

CREATE TABLE cliente ( 
	id int(11) not null auto_increment, 
	nome varchar(100) not null, 
	CPF varchar(11) not null,
	data_nascimento DATE not null,
	constraint pk_cliente primary key (id) );

INSERT INTO cliente (nome, CPF, data_nascimento) VALUES ('Lion', '12312312312', '2019-06-02');
INSERT INTO cliente (nome, CPF, data_nascimento) VALUES ('Slark', '45645645645', '2019-06-03');
INSERT INTO cliente (nome, CPF, data_nascimento) VALUES ('Luna', '78978978978', '2019-06-04');
INSERT INTO cliente (nome, CPF, data_nascimento) VALUES ('Pudge', '23423423423', '2019-06-05');
INSERT INTO cliente (nome, CPF, data_nascimento) VALUES ('Mirana', '56756756756', '2019-06-06');
commit;

CREATE TABLE pedido ( 
	id int(11) not null auto_increment, 
	id_cliente int(11) not null, 
	valor decimal(10,2),
	FOREIGN KEY fk_pedido_id_cliente(id_cliente) REFERENCES cliente(id),
	CONSTRAINT pk_produto PRIMARY KEY (id) );

INSERT INTO pedido(id_cliente, valor) VALUES (1, 141.00);
INSERT INTO pedido(id_cliente, valor) VALUES (2, 259.75);
INSERT INTO pedido(id_cliente, valor) VALUES (3, 190.75);
INSERT INTO pedido(id_cliente, valor) VALUES (4, 172.50);
INSERT INTO pedido(id_cliente, valor) VALUES (5, 151.15);
commit;

CREATE TABLE produto ( 
	id int(11) not null auto_increment, 
	nome varchar(100) not null, 
	preco_sugerido decimal(10,2),
	CONSTRAINT pk_produto primary key (id) );

INSERT INTO produto(nome, preco_sugerido) VALUES ('Tranquil Boots', 10.50);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Aghanim\'s Schepter', 42.00);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Black King Bar', 40.50);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Yasha and Kaya', 41.00);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Blink Dagger', 22.50);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Ghost Schepter', 15.00);		
INSERT INTO produto(nome, preco_sugerido) VALUES ('Aether Lens', 23.50);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Eul\'s Scepter', 27.50);		
INSERT INTO produto(nome, preco_sugerido) VALUES ('Silver Edge', 55.50);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Monkey King Bar', 41.75);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Abyssal Blade', 66.50);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Power Treads', 14.50);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Echo Sabre', 26.50);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Eye of Skadi', 55.00);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Dragon Lance', 19.00);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Butterfly', 54.75);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Manta Style', 47.00);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Blade Mail', 22.00);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Heart of Tarrasque', 52.00);
INSERT INTO produto(nome, preco_sugerido) VALUES ('Wraith Band', 5.15);	
INSERT INTO produto(nome, preco_sugerido) VALUES ('Desolator', 35.00);
commit;

CREATE TABLE item_pedido ( 
	id int(11) not null auto_increment, 
	id_pedido int(11) not null, 
	id_produto int(11) not null,
	quantidade decimal(10,2),
	preco decimal(10,2),
	FOREIGN KEY fk_item_pedido_id_pedido(id_pedido) REFERENCES pedido(id),
	FOREIGN KEY fk_item_pedido_id_produto(id_produto) REFERENCES produto(id),
	CONSTRAINT pk_item_pedido PRIMARY KEY (id) );

INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (1, 1, 1, 10.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (1, 8, 1, 27.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (1, 7, 1, 23.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (1, 2, 1, 42.00);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (1, 5, 1, 22.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (1, 6, 1, 15.00);

INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (2, 10, 1, 41.75);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (2, 9, 1, 55.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (2, 11, 1, 66.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (2, 12, 1, 14.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (2, 13, 1, 26.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (2, 14, 1, 55.50);

INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (3, 15, 1, 19.00);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (3, 9, 1, 55.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (3, 16, 1, 54.75);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (3, 12, 1, 14.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (3, 17, 1, 47.00);

INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (4, 5, 1, 22.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (4, 18, 1, 22.00);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (4, 19, 1, 52.00);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (4, 1, 1, 10.50);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (4, 2, 1, 42.00);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (4, 7, 1, 23.50);

INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (5, 21, 1, 35.00);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (5, 16, 1, 54.75);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (5, 10, 1, 41.75);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (5, 20, 1, 5.15);
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco) VALUES (5, 12, 1, 14.50);
commit;
