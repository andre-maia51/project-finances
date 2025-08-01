# API de Controle de Despesas

![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.3-green?logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-blue?logo=docker)

## 📖 Sobre o Projeto

É uma API RESTful para gestão de finanças pessoais, desenvolvida como um projeto de aprofundamento em tecnologias de back-end modernas. A aplicação permite que utilizadores se registem, autentiquem de forma segura e giram as suas finanças pessoais através da criação de contas, categorias e lançamento de transações (receitas e despesas).

O grande diferencial deste projeto é a sua arquitetura, que é inteiramente containerizada com Docker, garantindo um ambiente de desenvolvimento e produção consistente, seguro e fácil de configurar.

## ✨ Features Principais

-   **Ambiente Totalmente Dockerizado**: A aplicação e a base de dados PostgreSQL rodam em contentores orquestrados pelo Docker Compose, permitindo que todo o ambiente seja iniciado com um único comando.
-   **Autenticação e Autorização com JWT**: Sistema de segurança completo usando Spring Security para proteger os endpoints. O fluxo inclui registo de utilizador com encriptação de senha (BCrypt) e um endpoint de login que gera um Token JWT para autenticação stateless.
-   **CRUD Completo e Seguro por Utilizador**:
    -   Gestão completa de **Contas** (`POST`, `GET`, `PUT`, `DELETE`).
    -   Gestão completa de **Categorias** (`POST`, `GET`, `PUT`, `DELETE`).
    -   Gestão completa de **Transações** (`POST`, `GET`, `PUT`, `DELETE`)
-   **Regras de Negócio Robustas**: A exclusão de contas ou categorias é bloqueada caso existam transações associadas, garantindo a integridade do histórico financeiro.
-   **Configuração Segura**: Utilização de variáveis de ambiente (`.env`) para gerir dados sensíveis, sem expor credenciais no controlo de versões.
-   **Migrations com Flyway**: O esquema da base de dados é criado e versionado de forma automática e segura.

## 🛠️ Tecnologias Utilizadas

-   **Java 21**
-   **Spring Boot 3.5.3**
-   **Spring Security**
-   **JWT (Auth0 Java JWT)**
-   **Spring Data JPA**
-   **PostgreSQL**
-   **Flyway**
-   **Docker & Docker Compose**
-   **Maven**
-   **Lombok**

## 🚀 Como Executar o Projeto

Graças ao Docker, configurar e executar o projeto é extremamente simples.

### Pré-requisitos
-   **Docker**
-   **Docker Compose**

### Configuração

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/andre-maia51/project-finances.git](https://github.com/andre-maia51/project-finances.git)
    cd project-finances
    ```

2.  **Crie e configure o arquivo de ambiente:**
    -   Copie o arquivo de exemplo `.env.example` para um novo arquivo chamado `.env`.
        ```bash
        cp .env.example .env
        ```
    -   Abra o arquivo `.env` e preencha as variáveis com os seus valores desejados (utilizador, senha e nome do banco de dados).

3.  **Suba o ambiente com Docker Compose:**
    ```bash
    docker-compose up --build
    ```
    Este comando irá construir a imagem da sua aplicação Spring Boot, baixar a imagem do PostgreSQL e iniciar os dois contentores, já conectados e configurados.

A API estará disponível em `http://localhost:8080`.

## 📡 API Endpoints

### Autenticação

#### Registar um novo utilizador
-   **Método**: `POST`
-   **URL**: `/users/register`
-   **Body**: `UserDTO`

#### Fazer login
-   **Método**: `POST`
-   **URL**: `/login`
-   **Body**: `LoginDTO`
-   **Resposta**: `TokenDataDTO` com o token JWT.

---
**⚠️ Os endpoints abaixo requerem autenticação (Bearer Token).**
---

### Contas (Accounts)

-   `POST /accounts`: Criar uma nova conta.
-   `GET /accounts`: Listar todas as contas do utilizador autenticado.
-   `GET /accounts/{id}`: Obter os detalhes de uma conta específica.
-   `PUT /accounts/{id}`: Atualizar o nome de uma conta.
-   `DELETE /accounts/{id}`: Apagar uma conta (só é permitido se não tiver transações associadas).

### Categorias (Categories)

-   `POST /categories`: Criar uma nova categoria.
-   `GET /categories`: Listar todas as categorias do utilizador autenticado.
-   `GET /categories/{id}`: Obter os detalhes de uma categoria específica.
-   `PUT /categories/{id}`: Atualizar o nome e a descrição de uma categoria.
-   `DELETE /categories/{id}`: Apagar uma categoria (só é permitido se não tiver transações associadas).

### Transações (Transactions)

-   `POST /transactions`: Lançar uma nova transação (receita ou despesa).
-   `GET /transactions`: Listar todas as transações do utilizador.
    -   **Filtro opcional:** `GET /transactions?year={ano}&month={mes}`
-   `PUT /transactions/{id}`: Atualizar os detalhes de uma transação.
-   `DELETE /transactions/{id}`: Apagar uma transação (o saldo da conta é recalculado).

## 👨‍💻 Autor

**André Maia**

-   **GitHub:** [@andre-maia51](https://github.com/andre-maia51)
-   **LinkedIn:** [andre-maia-cunha](https://www.linkedin.com/in/andre-maia-cunha/)
