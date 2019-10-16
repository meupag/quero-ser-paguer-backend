# Store

## Descrição

Serviço REST que simula uma loja onde clientes podem fazer pedidos de produtos.

Exemplos de requisições são apresentados no arquivo: "Store - Postman.postman_collection".

Testes untários e de integração estão disponíveis em "test.java.com.store".

Executar como Docker: "docker-compose up"

## Domain 

Customer, Order, OrderItem, Product.

Customers e Products jamais são apagados do banco (ficam com o status de "inativo").

## Tecnologias utilizadas

- Java
- Maven
- Spring Framework (Spring boot, Spring boot actuator, Spring Data JPA, Spring HATEOAS)
- JUnit e Mockito
- Docker
- Swagger
- Lombok
- Hibernate
- MySQL e H2 (Banco para Teste)
