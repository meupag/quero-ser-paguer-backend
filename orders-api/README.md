
# Api de pedidos
A API de pedidos é responsável por manipular pedidos e produtos 


## Plataforma

O projeto foi desenvolvido utilizando Spring Boot usando JPA e Bean Validation


## Arquitetura 
Partindo da ideia de que uma entidade de Pedido pode agregar regras de negócio complexas foi adotado uma implementação do Domain Driven Design.
Para modelar o projeto orientado ao dominio foram adotadas as seguintes camadas

**Application** - Contem a controller e as view model junto com o Bean Validation (Nessa camada também é realizada a injeção de dependência dos serviços e repositórios - apesar do comum fosse utilizar um container que realize a inversão de controle ) 

**Domain** - Contem as entidades e regras de negócio da aplicação, assim como as interfaces de repositório.

**Infrastructure** - Contem a implementação dos repositórios.
 
**Services** - Contem as classes responsáveis por se comunicar com  serviços externos como a API de clientes. 
