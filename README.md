# API de Consentimentos - Desafio Sensedia

## 📋 Visão Geral
API REST para gerenciamento de consentimentos de usuários, desenvolvida como solução para o desafio técnico da Sensedia.

## ✨ Funcionalidades
- ✅ CRUD completo de consentimentos
- ✅ Validação de CPF
- ✅ Auditoria de alterações
- ✅ Integração com serviços externos
- ✅ Monitoramento com Prometheus/Grafana
- ✅ Documentação Swagger

## 🛠️ Tecnologias
| Tecnologia         | Descrição                |
|--------------------|--------------------------|
| Java 21            | Linguagem principal      |
| Spring Boot 3.x    | Framework Java           |
| JUnit e Mockito    | Testes unitários         |
| MongoDB            | Banco de dados principal |
| Testcontainers     | Testes de integração     |
| Prometheus+Grafana | Monitoramento            |
| Loki               | Gerenciamento de logs    |

## 🚀 Como Executar

### Pré-requisitos
- Docker 20+
- Java 21
- Maven 3.9+

### Com Docker
```bash
docker-compose -f implementacao/docker-compose.yml up -d
```

### Localmente
```bash
mvn spring-boot:run
```

## 📡 Endpoints

### Criar Consentimento (POST)
```http
curl -X POST "http://localhost:8099/consents" \
-H "Content-Type: application/json" \
-d '{
  "cpf": "123.456.789-09",
  "status": "ACTIVE",
  "expirationDateTime": "2025-12-31T23:59:59",
  "additionalInfo": "Consentimento para marketing"
}'
```

**Resposta:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "cpf": "123.456.789-09",
  "status": "ACTIVE",
  "creationDateTime": "2024-04-22T10:30:00Z",
  "additionalInfo": "Consentimento"
}
```


### Criar Consentimento com chamada para api do gitHub https://api.github.com/users/martinfowler (POST)
```http
curl -X POST "http://localhost:8099/consents/with-external-info" \
-H "Content-Type: application/json" \
-d '{
  "cpf": "123.456.789-09",
  "status": "ACTIVE",
  "expirationDateTime": "2025-12-31T23:59:59",
}'
```

**Resposta:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "cpf": "123.456.789-09",
  "status": "ACTIVE",
  "creationDateTime": "2024-04-22T10:30:00Z",
  "additionalInfo": "martinfowler"
}
```

### Listar Consentimentos (GET)
```http
curl "http://localhost:8099/consents"
```

### Detalhes do Consentimento (GET)
```http
curl "http://localhost:8099/consents/{id}"
```

### Atualizar Consentimento (PUT)
```
curl -X PUT "http://localhost:8099/consents/550e8400-e29b-41d4-a716-446655440000" \
-H "Content-Type: application/json" \
-d '{
  "status": "REVOKED",
  "expirationDateTime": "2025-11-31T23:59:59",
  "additionalInfo": "Novo Consentimento"
}'
```

### Deletar Consentimento (DELETE)
```
curl -X DELETE "http://localhost:8099/consents/550e8400-e29b-41d4-a716-446655440000"
```

## 🧪 Testes
```bash
mvn test
```

## 📊 Monitoramento
| Serviço      | URL                          | Credenciais      |
|--------------|------------------------------|------------------|
| Prometheus   | http://localhost:9090        | -                |
| Grafana      | http://localhost:3000        | admin/admin      |
| Loki         | http://localhost:3100        | -                |

## 📚 Documentação
Acesse a documentação completa no Swagger:
```
http://localhost:8099/swagger-ui/index.html
```

## 🏗️ Estrutura do Projeto
```
consents/
├── src/
│   ├── main/
│   │   ├── java/com/sensedia/sample/consents
│   │   │   ├── config/
            ├── domain/
            ├── dto/
            ├── exceptions/
            ├── mapper/
            ├── repository/
            ├── resource/   
│   │   │   └── resource/
│   │   └── resources/
│   └── test/
├── implementacao/
│   └── observability/
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## ⚠️ Tratamento de Erros
Exemplo de resposta de erro:
```json
{
  "timestamp": "2024-04-22T10:45:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "CPF inválido"
}
```

## 📦 Build Imagem Docker
```bash
docker build -t consent-api:latest .
```