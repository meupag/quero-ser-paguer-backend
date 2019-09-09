
# Api de clientes

A API de cliente é responsável pela manipulação e criação de clientes na plataforma.


## Plataforma

O projeto foi desenvolvido em NodeJS 10 utilizando as seguintes bibliotecas/módulos

 - Sequelize - ORM para manipulação de dados 
 - Swagger - No caso está sendo utilizado o pacote `swagger-node` , ele é responsável por rotear a API e definir objetos de requisição e resposta, além de validações básicas
 - Express - Framework para criação de aplicativos web javascript
## Arquitetura 
Visando entregar algo modular e de prática manutenção com regras de negócio centralizada, foram adotadas o seguinte padrão de organização de pastas e componentes:

![Arquitetura](images/arquitetura_api.png)

 - **Swagger** - Realiza a validação dos objetos requisitados e direciona para controller selecionada
 - **Controller** - Realiza validações um pouco mais complexas como a verificação do numero do CPF e direciona para camada business a qual irá manipular o objeto e definir o retorno 
 - **Business** - Realiza a chama de serviços externos e da camada repository que persiste dados na base
 - **Repository** - Realiza a persistência de dados na base, note que para essa camada é utilizada uma variação sintática do JS, visando uma melhor organização do código.

 # Endpoints

### /client

#### POST
##### Description:

Cria um novo usuario/cliente na base

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| cliente | body | Cliente que será criado | No | [Cliente](#cliente) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Created | [Cliente](#cliente) |
| 400 | Bad request | [ [ErrorResponse](#errorresponse) ] |

### /client/{uuid}

#### GET
##### Description:

Cria um novo usuario/cliente na base

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| uuid | path | Identificador universal unico do usuário | Yes | string (uuid) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Cliente | [Cliente](#cliente) |
| 204 | Cliente não encontrado |  |
| 400 | Bad request | [ [ErrorResponse](#errorresponse) ] |
| 500 | Internal error | [ [ErrorResponse](#errorresponse) ] |

#### DELETE
##### Description:

Deleta um usuário da base e do Cognito

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| uuid | path | Identificador universal unico do usuário | Yes | string (uuid) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 201 | Cliente excluído com sucesso |  |
| 400 | Bad request | [ [ErrorResponse](#errorresponse) ] |
| 500 | Internal error | [ [ErrorResponse](#errorresponse) ] |

#### PATCH
##### Description:

Realiza um update de um usuário

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| uuid | path | Identificador universal unico do usuário | Yes | string (uuid) |
| client | body | Dados do usuário que será alterado | No | [ClienteUpdate](#clienteupdate) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 201 | Cliente alterado com sucesso |  |
| 400 | Bad request | [ [ErrorResponse](#errorresponse) ] |
| 500 | Internal error | [ [ErrorResponse](#errorresponse) ] |

### /clients

#### GET
##### Description:

Busca uma lista de usuários na base

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| limit | query | Limite de resultados | Yes | integer |
| offset | query | Página | Yes | integer |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Cliente | [ResponseListClient](#responselistclient) |
| 204 | Cliente não encontrado |  |
| 400 | Bad request | [ [ErrorResponse](#errorresponse) ] |
| 500 | Internal error | [ [ErrorResponse](#errorresponse) ] |

### /login

#### POST
##### Description:

Autentica um usuário

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| auth | body | Dados de login e senha do usuário | No | [Login](#login) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Logged | [LoginToken](#logintoken) |
| 400 | Bad request | [ [ErrorResponse](#errorresponse) ] |

### /swagger

### Models


#### ErrorResponse

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| message | string |  | Yes |
| field | string |  | No |
| type | string | Possivéis tipos de erro:     `REQUEST_INVALID_FORMAT` - Há um paramêtro com erro na requisição      `BUSINESS_VALIDATION` - A requisição não passou em alguma regra de negócio     `UNHANDLED_REQUEST_ERROR` - Erro interno de servidor     `REQUEST_ERROR` - Erro de requisição  | No |

#### Cliente

Dados de um cliente

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| nome | string | Nome do cliente que será criado | No |
| cpf | string | CPF do cliente | Yes |
| data_nascimento | string | Data de nascimento do cliente | No |
| email | string (email) | Email do cliente que será usado para login no cognito | Yes |
| password | string | Senha do usuário que será criado no cognito | No |
| username | string | Username que será criado no cognito | No |
| phoneNumber | string | Numero de telefone do cliente | No |

#### ClienteUpdate

Dados de um cliente que será alterado

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| nome | string | Nome do cliente que será criado | No |
| data_nascimento | string | Data de nascimento do cliente | No |
| email | string (email) | Email do cliente que será usado para login no cognito | No |
| phoneNumber | string | Numero de telefone do cliente | No |

#### ClientePostResponse

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string | Id do cliente criado | No |

#### ResponseListClient

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| total | integer |  | Yes |
| list | [ [Cliente](#cliente) ] |  | Yes |

#### Login

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| username | string | Username do usuário que efetuará o login | No |
| password | string | Senha do usuário | No |

#### LoginToken

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| idToken | object |  | Yes |
| refreshToken | object |  | Yes |
| accessToken | object |  | Yes |
| clockDrift | number |  | Yes | 
