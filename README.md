markdown
Copy Code

# FórumHub API

## Visão Geral

O FórumHub é uma API REST desenvolvida com o framework Spring, destinada a facilitar a gestão de tópicos em um fórum
online. Este projeto permite aos usuários realizar operações CRUD (Criar, Consultar, Atualizar, Deletar) em tópicos,
além de oferecer funcionalidades de autenticação e autorização para garantir a segurança e a integridade dos dados.

## Funcionalidades

-**Criar um novo tópico**: Permite aos usuários criar novos tópicos no fórum.

-**Mostrar todos os tópicos criados**: Lista todos os tópicos disponíveis no fórum.

-**Mostrar um tópico específico**: Exibe detalhes de um tópico específico.

-**Atualizar um tópico**: Permite a edição de um tópico existente.

-**Eliminar um tópico**: Permite a exclusão de um tópico.

## Tecnologias Utilizadas

-**Spring Boot**: Framework utilizado para a criação da API.

-**Spring Security**: Utilizado para a implementação de autenticação e autorização.

-**JPA/Hibernate**: Para a persistência dos dados em uma base de dados relacional.

-**MySQL**: Sistema de gerenciamento de banco de dados relacional.

## Instalação e Execução

### Pré-requisitos

- Java JDK 11 ou superior
- Maven
- MySQL Server

### Configuração do Banco de Dados

1. Crie um banco de dados no MySQL:

```
sql
   CREATE DATABASE forumhub;
```

1. Atualize o arquivo`application.properties`com as credenciais do seu banco de dados:

    ```
    properties
    Copy Code
    spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    
    ```

### Executando a Aplicação

1. Clone o repositório:

    ```shell
    Copy Code
    git clone https://github.com/seu-usuario/forumhub.git
    cd forumhub
    
    ```

2. Compile e execute a aplicação usando Maven:

    ```bash
    Copy Code
    mvn clean install
    mvn spring-boot:run
    
    ```

## Uso da API

Você pode acessar a API através de qualquer cliente HTTP, como Postman ou curl. Aqui estão alguns exemplos de como
interagir com a API:

- **Criar um novo tópico**:

    ```bash
    Copy Code
    curl -X POST http://localhost:8080/topicos -H 'Content-Type: application/json' -d '{"titulo": "Novo Tópico", "conteudo": "Conteúdo do novo tópico"}'
    ```

- **Listar todos os tópicos**:

    ```bash
    Copy Code
    curl -X GET http://localhost:8080/topicos
    ```

- **Atualizar um tópico**:

    ```bash
    Copy Code
    curl -X PUT http://localhost:8080/topicos/{id} -H 'Content-Type: application/json' -d '{"titulo": "Título Atualizado", "conteudo": "Conteúdo atualizado"}'
    ```

- **Deletar um tópico**:

    ```bash
    Copy Code
    curl -X DELETE http://localhost:8080/topicos/{id}
    ```