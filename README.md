# 🧠 Sistema de Gestão de Projetos e Demandas

API RESTful desenvolvida em Java com Spring Boot para gerenciar projetos e tarefas de uma equipe de desenvolvimento.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Execução](#instalação-e-execução)
- [Documentação da API](#documentação-da-api)
- [Modelagem de Dados](#modelagem-de-dados)
- [Testes](#testes)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Decisões Técnicas](#decisões-técnicas)
- [Autor](#autor)

## 🎯 Sobre o Projeto

Sistema desenvolvido para gerenciar projetos e suas respectivas tarefas, permitindo:
- Criação e listagem de projetos
- Gerenciamento completo de tarefas (CRUD)
- Filtros avançados por status, prioridade e projeto
- Rastreamento do ciclo de vida das tarefas

## 🚀 Tecnologias Utilizadas

### Core
- **Java 17**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Spring Validation**

### Banco de Dados
- **H2 Database** (desenvolvimento)
- **PostgreSQL** (produção - opcional)

### Documentação
- **Swagger/OpenAPI 3.0**

### Testes
- **JUnit 5**
- **Mockito**
- **MockMvc**
- **RestAssured** (testes de API)

### DevOps
- **Docker & Docker Compose**
- **Maven**

### Extras
- **MapStruct** (mapeamento de DTOs)
- **Lombok** (redução de boilerplate)

## 📦 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Docker e Docker Compose (opcional)

## 🔧 Instalação e Execução

### Opção 1: Com Docker (Recomendado)
```bash
# Clone o repositório
git clone <seu-repositorio>
cd <nome-do-projeto>

# Execute com Docker Compose
docker-compose up -d

# A aplicação estará disponível em http://localhost:8080
```

### Opção 2: Localmente com Maven
```bash
# Clone o repositório
git clone <seu-repositorio>
cd <nome-do-projeto>

# Compile o projeto
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

### Opção 3: Executando o JAR
```bash
# Compile o projeto
mvn clean package

# Execute o JAR gerado
java -jar target/inscritos-0.0.1-SNAPSHOT.jar
```

## 📚 Documentação da API

Após iniciar a aplicação, acesse:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Endpoints Principais

#### Projetos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/projects` | Criar novo projeto |
| GET | `/api/projects` | Listar todos os projetos |
| GET | `/api/projects/{id}` | Buscar projeto por ID |
| PUT | `/api/projects/{id}` | Atualizar projeto |
| DELETE | `/api/projects/{id}` | Deletar projeto |

#### Tarefas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/tasks` | Criar nova tarefa |
| GET | `/api/tasks` | Listar tarefas com filtros |
| GET | `/api/tasks/{id}` | Buscar tarefa por ID |
| PUT | `/api/tasks/{id}/status` | Atualizar status da tarefa |
| PUT | `/api/tasks/{id}` | Atualizar tarefa completa |
| DELETE | `/api/tasks/{id}` | Deletar tarefa |

### Exemplos de Requisições

#### Criar Projeto
```json
POST /api/projects
Content-Type: application/json

{
  "name": "Sistema de Vendas",
  "description": "Desenvolvimento do sistema de vendas online",
  "startDate": "2025-01-01",
  "endDate": "2025-12-31"
}
```

#### Criar Tarefa
```json
POST /api/tasks
Content-Type: application/json

{
  "title": "Implementar autenticação JWT",
  "description": "Desenvolver módulo de autenticação com JWT",
  "status": "TODO",
  "priority": "HIGH",
  "dueDate": "2025-02-15",
  "projectId": 1
}
```

#### Filtrar Tarefas
```
GET /api/tasks?status=TODO&priority=HIGH&projectId=1
```

## 💾 Modelagem de Dados

### Entidade Project
```java
{
  "id": "Long",
  "name": "String (3-100 caracteres) - Obrigatório",
  "description": "String - Opcional",
  "startDate": "Date",
  "endDate": "Date - Opcional",
  "tasks": "List<Task>"
}
```

### Entidade Task
```java
{
  "id": "Long",
  "title": "String (5-150 caracteres) - Obrigatório",
  "description": "String",
  "status": "Enum [TODO, DOING, DONE]",
  "priority": "Enum [LOW, MEDIUM, HIGH]",
  "dueDate": "Date",
  "projectId": "Long - FK para Project"
}
```

### Diagrama de Relacionamento
```
Project (1) -----> (*) Task
```

## 🧪 Testes

### Executar todos os testes
```bash
mvn test
```

### Executar testes de integração
```bash
mvn verify
```

### Cobertura de Testes
```bash
mvn clean test jacoco:report
# Relatório gerado em: target/site/jacoco/index.html
```

## 📁 Estrutura do Projeto
```
src/
├── main/
│   ├── java/
│   │   └── dev/matheuslf/desafio/inscritos/
│   │       ├── config/          # Configurações (Swagger, etc)
│   │       ├── controller/      # REST Controllers
│   │       ├── dto/             # Data Transfer Objects
│   │       ├── entity/          # Entidades JPA
│   │       ├── enums/           # Enumerações (Status, Priority)
│   │       ├── exception/       # Tratamento de exceções
│   │       ├── mapper/          # MapStruct Mappers
│   │       ├── repository/      # Repositórios JPA
│   │       └── service/         # Lógica de negócio
│   └── resources/
│       ├── application.properties
└── test/
    └── java/
        └── dev/matheuslf/desafio/inscritos/
            ├── controller/      # Testes de Controllers
            ├── service/         # Testes de Services
            └── repository/     # Testes de Repository
```

## 🎨 Decisões Técnicas

### Banco de Dados
- **H2**: Utilizado para facilitar o desenvolvimento e execução local sem dependências externas
- **Caminho**: Configurado para `/data/db` com persistência em arquivo
- **Console H2**: Disponível em `/h2-console` para inspeção durante desenvolvimento

### Validações
- Bean Validation aplicado em todos os DTOs
- Validações customizadas para regras de negócio específicas

### Tratamento de Erros
- `@ControllerAdvice` centralizado para captura de exceções
- Respostas padronizadas com mensagens descritivas

### DTOs
- Uso de `record` do Java 17 para DTOs imutáveis
- Separação clara entre camadas (Entity ↔ DTO)

### Docker
- Container otimizado com multi-stage build
- Volume persistente para banco de dados H2 em memória
- Configuração pronta para produção

## 🔐 Segurança

### Autenticação JWT
```bash
# Login
POST /api/auth/login
{
  "login": "admin",
  "password": "senha"
}
```

## 🐳 Docker

### Dockerfile
```dockerfile
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### docker-compose.yml
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
```

## 👨‍💻 Autor

**Seu Nome**
- GitHub: [@HernandoJunior](https://github.com/HernandoJunior)
- LinkedIn: [Hernando Junior](https://www.linkedin.com/m/in/hernandojunior/)
- Email: devhernandojunior@gmail.com

## 📄 Licença

Este projeto foi desenvolvido exclusivamente para o **Desafio dos incritos do Professor Matheus Ferreira** e não deve ser utilizado para fins comerciais.

---

**Desenvolvido com carinho usando Spring Boot**

## 🏷️ Tags
`#Java` `#SpringBoot` `#Backend` `#API` `#RestAPI` `#Docker` `#PostgreSQL` `#H2` `#JPA` `#Swagger` `#RestAssured` `#CleanCode` `#SoftwareEngineering`