# SoulUp API — Domain Driven Design Using Java (Sprint 4)

API RESTful do **SoulUp**, plataforma que transforma ações sustentáveis em pontos,
níveis e recompensas, com o avatar Soul acompanhando a evolução do usuário.
A API é consumida pelo front-end em React (Front-End Design Engineering) e grava no
mesmo banco Oracle usado em Building Relational Database e no sistema em Python.

**Equipe SoulUp · Turma 1TDSPO**

| Integrante | RM |
|---|---|
| Matheus Ruiz Giatti | 570701 |
| Matheus Leite Souza das Virgens | 573416 |

## Links

- **API publicada (Render):** <https://soulup-api-k9nq.onrender.com>
- **Documentação interativa (Swagger):** <https://soulup-api-k9nq.onrender.com/swagger-ui.html>
- **Front-end (Vercel):** _preencher após o deploy do front_

## Tecnologias

- Java 21 e Spring Boot 4.1.1 (Spring Web MVC, Spring Data JPA, Bean Validation)
- Oracle Database (servidor da FIAP) com o driver ojdbc11
- springdoc-openapi (Swagger UI)
- Docker para o deploy no Render

## Organização em camadas

```
src/main/java/br/com/fiap/soulup/
├── SoulupApiApplication.java   classe principal
├── ServletInitializer.java
├── config/CorsFilter.java      libera o acesso do front-end (outro domínio)
├── model/                      entidades JPA: as 10 tabelas do banco
├── repository/                 acesso aos dados (DAO) com JpaRepository
├── service/                    regras de negócio (BO)
├── dto/                        dados de entrada e saída (records)
└── controller/                 endpoints REST (resource)
```

## Endpoints

| Verbo | URI | Descrição | Status |
|---|---|---|---|
| GET | `/` | Verifica se a API está no ar | 200 |
| GET | `/usuarios` | Lista os usuários em ordem de ranking | 200 |
| GET | `/usuarios/{id}` | Busca um usuário | 200, 404 |
| POST | `/usuarios` | Cadastra usuário e cria o avatar Soul | 201, 400, 409 |
| PUT | `/usuarios/{id}` | Atualiza nome e e-mail | 200, 400, 404, 409 |
| DELETE | `/usuarios/{id}` | Exclui o usuário e o histórico dele | 204, 404 |
| GET | `/usuarios/{id}/registros` | Histórico de ações do usuário | 200, 404 |
| GET | `/usuarios/{id}/missoes` | Missões com status e progresso do usuário | 200, 404 |
| GET | `/acoes` | Catálogo de ações sustentáveis | 200 |
| GET | `/missoes` | Catálogo de missões | 200 |
| GET | `/registros/{id}` | Busca um registro de ação | 200, 404 |
| POST | `/registros` | Registra uma ação, soma pontos e conclui missões | 201, 400, 404 |
| PUT | `/registros/{id}` | Corrige um registro e ajusta os pontos | 200, 400, 404, 409 |
| DELETE | `/registros/{id}` | Exclui um registro e retira os pontos | 204, 404, 409 |

Erros de servidor ou de conexão com o banco retornam **500**.

### Regras de negócio

- Nível pela experiência: 1 (0 pt), 2 (50), 3 (200), 4 (500) e 5 (1000). A experiência
  define o nível; o saldo é o que pode ser gasto em resgates.
- A pontuação de cada registro vem do catálogo de ações, e o impacto é calculado por ela:
  MINIMO (até 19), BAIXO (20 a 49), MEDIO (50 a 79) e ALTO (80 a 100).
- Ao registrar uma ação, as missões cuja meta foi atingida são concluídas e o bônus é
  creditado uma única vez, na mesma transação.
- O e-mail é único, sem diferenciar maiúsculas e minúsculas.
- Não é possível retirar pontos que já foram gastos em resgates.

## Como executar

### Pré-requisitos

- JDK 21 e IntelliJ IDEA (ou outra IDE com Maven)
- Conta no Oracle da FIAP com as tabelas do SoulUp criadas: script DDL de Building
  Relational Database e o script `SoulUp_SPRINT4.sql` (sequências e colunas de missões),
  entregue na disciplina de Python.

### Passo a passo no IntelliJ

1. Abra a pasta do projeto (File > Open) e aguarde o Maven baixar as dependências.
2. Em **Run > Edit Configurations**, na configuração `SoulupApiApplication`, preencha
   **Environment variables** com `DB_USER=seu_rm;DB_PASSWORD=sua_senha`.
3. Execute `SoulupApiApplication`.
4. Acesse `http://localhost:8081/swagger-ui.html` para testar os endpoints.

O usuário e a senha do banco ficam em variáveis de ambiente para não serem publicados
no GitHub. Opcionalmente, `DB_URL` troca o endereço do banco.

### Deploy no Render

O projeto possui um `Dockerfile`. No Render, crie um **Web Service** a partir do
repositório, escolha o ambiente **Docker** e cadastre as variáveis `DB_USER` e
`DB_PASSWORD`. O Render informa a porta pela variável `PORT`, já lida pela aplicação.

No plano gratuito o serviço "dorme" sem uso; a primeira requisição pode levar cerca de
um minuto.
