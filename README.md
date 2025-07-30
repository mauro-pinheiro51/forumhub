# 🧠 **ForumHub API**

API RESTful desenvolvida com **Spring Boot** para um fórum de discussões. Gerencia tópicos com autenticação JWT e controle de acesso baseado em usuários.

---

## 📑 **Sumário**

* [📌 Visão Geral](#visão-geral)
* [🚀 Funcionalidades Principais](#funcionalidades-principais)
* [🛠 Tecnologias Utilizadas](#tecnologias-utilizadas)
* [▶️ Como Executar a Aplicação](#como-executar-a-aplicação)
* [📮 Endpoints Essenciais para Avaliação](#endpoints-essenciais-para-avaliação)
* [🔐 Autenticação e Autorização](#autenticação-e-autorização)
* [🙏 Agradecimento](#agradecimento)

---

## 📌 **Visão Geral**

O **ForumHub** é uma plataforma onde usuários autenticados podem criar, visualizar, atualizar e "excluir" (inativar) tópicos de discussão.  
A API assegura que **apenas o autor do tópico** tenha permissão para modificá-lo ou inativá-lo.

---

## 🚀 **Funcionalidades Principais**

- ✅ **Autenticação JWT:** Login e geração de token.
- 📝 **Criação de Tópicos:** Atribuição automática do autor e status inicial `NAO_SOLUCIONADO`.
- 📄 **Listagem de Tópicos:** Apenas tópicos ativos, com paginação.
- 🔍 **Detalhamento:** Exibe dados completos de um tópico.
- ✏️ **Atualização:** Permitida **somente ao autor**.
- ❌ **Inativação lógica:** Exclusão lógica por parte do autor.

---

## 🛠 **Tecnologias Utilizadas**

- Java 17  
- Spring Boot 3.3.1 *(Web, JPA, Security, Validation)*  
- Maven  
- MySQL  
- FlywayDB  
- JJWT  
- Lombok  

---

## ▶️ **Como Executar a Aplicação**

1. **Clone o Projeto:**
   ```bash
   git clone <URL_DO_SEU_REPOSITORIO>
   cd apitopicos
   ```

2. **Configure o Banco de Dados:**

   No arquivo:
   ```
   src/main/resources/application.properties
   ```
   Ajuste as configurações com seu usuário/senha do MySQL.  
   Exemplo:
   ```
   spring.datasource.username=root
   spring.datasource.password=sua_senha
   ```

3. **Execute as Migrations (Flyway):**

   Ao iniciar a aplicação, as tabelas `usuarios` e `topicos` serão criadas automaticamente.  
   Verifique os scripts SQL em:
   ```
   src/main/resources/db/migration
   ```

4. **Suba a Aplicação:**
   ```bash
   mvn spring-boot:run
   ```
   Acesse via: [http://localhost:8080](http://localhost:8080)

---

## 📮 **Endpoints Essenciais para Avaliação**

Recomenda-se testar via Insomnia ou Postman.

### 🔑 1. Login (Obter Token JWT)
```http
POST http://localhost:8080/login
```
**Body (JSON):**
```json
{
  "login": "seu_usuario_de_teste",
  "senha": "sua_senha_de_teste"
}
```
📌 O token será retornado no `Authorization`.

---

### 🆕 2. Criar Tópico
```http
POST http://localhost:8080/topicos
```
**Auth:** `Bearer <token>`

**Body (JSON):**
```json
{
  "titulo": "Título do Meu Tópico",
  "mensagem": "Conteúdo da minha mensagem.",
  "autor": "seu_usuario_de_teste",
  "curso": "Nome do Curso"
}
```

---

### 📋 3. Listar Tópicos
```http
GET http://localhost:8080/topicos
```
**Auth:** `Bearer <token>`

---

### 🔍 4. Detalhar Tópico
```http
GET http://localhost:8080/topicos/{id}
```
**Auth:** `Bearer <token>`

---

### ✏️ 5. Atualizar Tópico
```http
PUT http://localhost:8080/topicos/{id}
```
**Auth:** `Bearer <token>`

**Body (JSON):**
```json
{
  "titulo": "Título Atualizado",
  "mensagem": "Mensagem atualizada."
}
```

✅ Retorna `200 OK` se for o autor  
❌ Retorna `403 Forbidden` se não for

---

### ❌ 6. Excluir (Inativar) Tópico
```http
DELETE http://localhost:8080/topicos/{id}
```
**Auth:** `Bearer <token>`

✅ `204 No Content` (se for o autor)  
❌ `403 Forbidden` (se não for o autor)

---

## 🔐 **Autenticação e Autorização**

- Todas as rotas exigem um token JWT no cabeçalho:
  ```
  Authorization: Bearer <seu_token>
  ```
- Somente o autor do tópico pode atualizá-lo ou excluí-lo.

---

## 🙏 **Agradecimento**

Agradecimentos à **ONE** e à **Alura** por toda a estrutura, conteúdo e inspiração.  
Aprender Java com Spring Boot tem sido uma jornada empolgante — e este projeto marca um importante passo nessa trilha.
