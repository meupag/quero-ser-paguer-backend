
![](logo_pag.png)

# orders-api
Esse projeto disponibiliza uma API para CRUD nas tabelas relacionadas no modelo de dados abaixo:

![](modelo-dados.png)

## Os recursos
### /products
|  Verbo | Formato  | Descrição |
| ------------ | ------------ | ------------ |
|  GET |  /products |  Retorna uma lista de todos os produtos cadastrados |
| GET | /products/{id}  | Retorna, se existir, o produto cadastrado sob id = {id}  |
| POST  | /products  | Permite o cadastro de um novo produto  |
| PUT  | /products/{id}  |  Permite atualizar um produto já existente |
| DELETE  | /products/{id}  | Permite deletar um produto existente |

### /clients
|  Verbo | Formato  | Descrição |
| ------------ | ------------ | ------------ |
|  GET |  /clients |  Retorna uma lista de todos os clientes cadastrados |
| GET | /clients/{id}  | Retorna, se existir, o cliente cadastrado sob id = {id}  |
| POST  | /clients  | Permite o cadastro de um novo cliente  |
| PUT  | /clients/{id}  |  Permite atualizar um cliente já existente |
| DELETE  | /clients/{id}  | Permite deletar um cliente existente |

### /orders
|  Verbo | Formato  | Descrição |
| ------------ | ------------ | ------------ |
|  GET |  /orders |  Retorna uma lista de todos os pedidos cadastrados |
| GET | /orders/{id}  | Retorna, se existir, o pedido cadastrado sob id = {id}  |
| POST  | /orders  | Permite o cadastro de um novo pedido  |
| PUT  | /orders/{id}  |  Permite atualizar um pedido já existente |
| DELETE  | /orders/{id}  | Permite deletar um pedido existente |

### /orderItems
|  Verbo | Formato  | Descrição |
| ------------ | ------------ | ------------ |
|  GET |  /orderItems |  Retorna uma lista de todos os itens de pedido cadastrados |
| GET | /orderItems/{id}  | Retorna, se existir, o item de pedido cadastrado sob id = {id}  |
| POST  | /orderItems  | Permite o cadastro de um novo item de pedido  |
| PUT  | /orderItems/{id}  |  Permite atualizar um item de pedido já existente |
| DELETE  | /orderItems/{id}  | Permite deletar um item de pedido existente |
> **PS:** o nome de todos os recursos e de todas as propriedades estão em inglês!

## Casos de uso
### listar todos os produtos
Requisição:

```json
{
	"url": "/products",
	"method": "GET"
}
```
Resposta:
```json
[
    {
        "id": 3,
        "name": "Torta de Limão",
        "suggestedPrice": 2.55
    },
    {
        "id": 4,
        "name": "Doritos",
        "suggestedPrice": 5.33
    },
    {
        "id": 5,
        "name": "Fandangos",
        "suggestedPrice": 3.2
    },
    {
        "id": 7,
        "name": "Feijão",
        "suggestedPrice": 1.99
    },
    {
        "id": 8,
        "name": "Arroz",
        "suggestedPrice": 5.99
    },
    {
        "id": 9,
        "name": "Fiat Uno",
        "suggestedPrice": 22000.99
    },
    {
        "id": 10,
        "name": "Chave de fenda",
        "suggestedPrice": 7.99
    },
    {
        "id": 11,
        "name": "Prateleira de plástico",
        "suggestedPrice": 22.98
    },
    {
        "id": 12,
        "name": "Nescau",
        "suggestedPrice": 4.67
    }
]
```
### recuperar um produto específico
Requisição:
```json
{
	"url": "/products/3",
	"method": "GET"
}
```
Resposta:
```json
{
    "id": 3,
    "name": "Torta de Limão",
    "suggestedPrice": 2.55
}
```

### cadastrar um novo produto
Requisição:
```json
{
	"url": "/products",
	"method": "POST"
	"body": {
		"name": "Jujuba",
		"suggestedPrice": 0.50
	}
}
```
Resposta:
```json
{
    "id": 13,
    "name": "Jujuba",
    "suggestedPrice": 0.5
}
```
### atualizar um produto existente
Requisição:
```json
{
	"url": "/products/13",
	"method": "POST"
	"body": {
		"suggestedPrice": 0.75
	}
}
```
Resposta:
```json
{
    "id": 13,
    "name": "Jujuba",
    "suggestedPrice": 0.75
}
```

## Entendendo as respostas da API
### código 500
 - método POST ou PUT sem corpo de requisição
 - propriedades no JSON que não fazem parte da entidade associada ao recurso
 - propriedades faltantes durante um POST
 - tentativa de persistir um relacionamento durante o POST ou PUT de uma entidade
 - tentativa de persistir uma entidade com relacionamentos que não existem no banco e que são de preenchimento obrigatório
### código 404
 - a entidade buscada no método GET não existe
 - nenhuma entidade cadastrada para o recurso
### código 204
 - DELETE efetuado com sucesso
### código 200
 - POST, PUT ou GET efetuados com sucesso

## A arquitetura
Para construção da API, optou-se por utilizar os serviços da AWS:
- cada endpoint da API é um Lambda
- as requisições para os Lambdas são gerenciadas pela API Gateway
- o banco de dados roda em um serviço RDS

No início do projeto, vislumbrou-se uma arquitetura baseada em Spring Boot, mas que pudesse ser deployada via Lambda. Existem inclusive referências na internet de projetos semelhantes, mas todos eles caem num problema de design fundamental: os Lambdas foram desenhados para suportar microserviços e não aplicações complexas e robustas como as que rodam sobre Spring. Dessa maneira, apesar da vantagem inicial de se utilizar de um framework bem estruturado e que nos força a seguir design patterns bem estabelecidos, as desvantagens não tardaram a surgir. Dentre elas:
- *cold start *dos Lambdas, o que ficaria ainda mais evidente dado o tamanho de um projeto construído com Spring
- a quebra total do paradigma de microserviços, pois o Lambda serviria apenas de proxy para uma aplicação convencional

>  na realidade faz muito mais sentido construir uma aplicação Spring e fazer deploy via EC2

Assim decidiu-se que o Spring não seria utilizado e que cada endpoint da API seria construído como um Lambda distinto. A API Gateway veio então como solução natural para o gerenciamento das requisições HTTP e a entrega de cada requisição ao endpoint apropriado.

Continuando com o paradigma da nuvem, que nos tira a necessidade de nos importar com infraestrutura, e permite um desenvolvimento mais ágil e entregas mais rápidas,  optou-se por utilizar um banco de dados instalado sobre RDS. A opção por DynamoDB não se demonstrou muito atrativa, dado que o próprio modelo de dados tomado como base é um modelo de dados relacional.

Como solução de ORM foi escolhido o Hibernate, que foi configurado para rodar com cache de segundo nível através da API do EhCache. E para facilitar a validação do modelo de dados, optou-se pela utilização do Hibernate Validation, que implementa a especificação do Bean Validation e se integra de maneira transparemente com o Hibernate, permitindo a validação de todas as entidades em momento de persistência.

## Trabalhos futuros (orders-api-2.0)
Nesta release inicial, trabalhou-se num esboço de como seria a autenticação de uma API REST utilizando o AWS Cognito. De maneira simples, a arquitetura de autenticação imaginada foi a seguinte:
1. o front-end faz uma requisição à API passando um usuário e senha (cadastrados previamente no Cognito User Pool)
2. a API responde com um token que será válido durante um tempo determinado
3. para cada nova requisição do front-end, será colocado no header da requisição uma tag *Authorization* referente ao token.

Nos commits inicias dessa release foram implementados os passos 2 e 3. Como o passo 1 é essencial para garantir a usabilidade da API, optou-se por remover essa solução e deixá-la como um *todo*.

Fica pendente para a próxima release a implementação de todos os testes unitários. De preferência, a metodologia de desenvolvimento deve ser aprimorada para incluir o TDD (red, green, refactor).

## A cereja do bolo
Esse repositório Git foi integrado ao Travis CI!!!

O Travis CI é um serviço muito interessante e que abre caminhos para facilitar o desenvolvimento e aumentar sua qualidade de código.
Toda vez que um repositório integrado ao Travis sofrer um commit, ele dispara uma máquina virtual que roda uma série de passos pré estabelecidos.
Para esse projeto, todo commit dispara um build do projeto e seu posterior deploy para uma CloudFormation.

Além disso, o projeto foi construído do zero a partir da Serverless Framework, que aliada ao Travis permite incorporar paradigmas de integração e melhoria contínua.
