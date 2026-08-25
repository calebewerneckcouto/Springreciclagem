# Aprendendo Spring

Projeto de aprendizado em **Spring Boot** com API REST de usuários, persistência em PostgreSQL, autenticação JWT e documentação via Swagger.

## Tecnologias

- Java 17
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT (jjwt 0.12.6)
- Lombok
- SpringDoc OpenAPI (Swagger UI)

## Estrutura do projeto

```
aprendendo-spring/
├── aprendendo-spring/          # Módulo Maven
│   ├── src/main/java/com/javanauta/aprendendo_spring/
│   │   ├── business/           # Regras de negócio
│   │   ├── controller/         # Endpoints REST
│   │   └── infrastructure/     # Entidades, repositórios, segurança e config
│   └── src/main/resources/
│       └── application.properties
└── README.md
```

## Pré-requisitos

- JDK 17+
- Maven 3.9+ (ou use o `./mvnw` incluído no projeto)
- PostgreSQL (testado com versão 9.5+)

## Configuração do banco

1. Crie o banco de dados:

```sql
CREATE DATABASE springjavanauta;
```

2. Ajuste as credenciais em `aprendendo-spring/src/main/resources/application.properties`, se necessário:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/springjavanauta
spring.datasource.username=postgres
spring.datasource.password=admin
```

3. Se o banco já existia com a coluna `senha` como `varchar(10)`, execute:

```sql
ALTER TABLE usuario ALTER COLUMN senha TYPE varchar(255);
```

> A senha é armazenada com hash BCrypt, que exige mais caracteres.

## Como executar

Entre na pasta do módulo Maven:

```bash
cd aprendendo-spring
```

### Via Maven

```bash
./mvnw spring-boot:run
```

No Windows:

```cmd
mvnw.cmd spring-boot:run
```

### Via IntelliJ

Abra o módulo `aprendendo-spring`, recarregue o Maven e execute a classe `AprendendoSpringApplication`.

A aplicação sobe na porta **8081**.

## Documentação da API (Swagger)

Com a aplicação rodando, acesse:

- http://localhost:8081/swagger-ui/index.html

## Endpoints

| Método | Rota | Autenticação | Descrição |
|--------|------|--------------|-----------|
| `POST` | `/usuario` | Não | Cadastra um usuário |
| `POST` | `/usuario/login` | Não | Autentica e retorna token JWT |
| `GET` | `/usuario?email={email}` | Sim | Busca usuário por e-mail |
| `DELETE` | `/usuario/{email}` | Sim | Remove usuário por e-mail |

### Cadastro

```http
POST /usuario
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456"
}
```

### Login

```http
POST /usuario/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "senha": "123456"
}
```

Resposta:

```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9...",
  "authorization": "Bearer eyJhbGciOiJIUzM4NCJ9..."
}
```

### Rotas protegidas

Envie o token no header:

```http
Authorization: Bearer {token}
```

Use **apenas** o valor do campo `token` (sem a palavra `Bearer` no Swagger — o botão **Authorize** já adiciona automaticamente).

## Autenticação no Swagger

1. Execute `POST /usuario/login`
2. Copie o campo **`token`** da resposta
3. Clique em **Authorize** (cadeado no topo)
4. Cole o token e confirme
5. Teste `GET /usuario` ou `DELETE /usuario/{email}`

## Observações

- O token JWT expira em **1 hora**
- Senhas são criptografadas com **BCrypt** no cadastro
- O projeto usa `PostgreSQLLegacyDialect` para compatibilidade com PostgreSQL 9.5
- Rotas do Swagger (`/swagger-ui/**` e `/v3/api-docs/**`) são públicas

## Testes

```bash
./mvnw test
```

## Licença

Projeto educacional — Javanauta.
