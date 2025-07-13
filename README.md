# FórumHub - API REST com Spring Boot

API RESTful de um fórum desenvolvida com Java e Spring Boot. Este projeto permite o cadastro, autenticação e gerenciamento de usuários e tópicos, com controle de acesso baseado em JWT (JSON Web Token), seguindo boas práticas de arquitetura e segurança.

## 🔧 Tecnologias Utilizadas

- Java 17  
- Spring Boot  
- Spring Data JPA  
- Spring Security  
- JWT (Auth0 Java JWT)  
- H2 Database (ou MySQL)  
- Maven  
- Insomnia / Postman (para testes)

## 🔐 Funcionalidades

- Cadastro e login de usuários com senha criptografada
- Autenticação via token JWT
- Listagem, criação, edição e exclusão de tópicos
- Proteção de rotas: apenas usuários autenticados podem gerenciar tópicos
- Validações com Bean Validation
- Código organizado em camadas (controller, service, repository, model, DTO)

## ▶️ Como executar

1. Clone o repositório:
   ```
   git clone https://github.com/seu-usuario/forumhub.git
   ```

2. Acesse o diretório do projeto:
   ```
   cd forumhub
   ```

3. Configure o `application.properties`:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
   spring.datasource.username=root
   spring.datasource.password=sua-senha
   jwt.secret=MinhaChaveSecretaParaJwtForumHubComSeguranca
   jwt.expiration=86400000
   ```

4. Execute o projeto com o Maven:
   ```
   ./mvnw spring-boot:run
   ```

## 🧪 Testes com JWT

1. Faça uma requisição `POST /login` com e-mail e senha para gerar o token:
   ```json
   {
     "email": "teste@email.com",
     "senha": "123456"
   }
   ```

2. Copie o token da resposta e utilize nas próximas requisições:
   ```
   Authorization: Bearer SEU_TOKEN_AQUI
   ```

## 📁 Estrutura do Projeto

```
src/
├── controller/
├── model/
├── dto/
├── repository/
├── security/
├── config/
└── service/
```

## 📝 Licença

Este projeto é licenciado sob a licença MIT.
