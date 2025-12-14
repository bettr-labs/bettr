# Documentação da API Bettr

## Base URL
Assumindo que a aplicação roda localmente na porta padrão (verifique o `application.yml` se necessário, geralmente é 8080).
`http://localhost:8080`

## Endpoints

### 1. Criar Conta (Enroll Account)

Cria uma nova conta de usuário.

- **URL:** `/accounts`
- **Método:** `POST`
- **Content-Type:** `application/json`

#### Corpo da Requisição

```json
{
  "nickname": "usuario_exemplo",
  "password": "senha_segura_123"
}
```

#### Resposta de Sucesso (201 Created)

```json
{
  "accountId": "uuid-da-nova-conta"
}
```

#### Resposta de Erro (409 Conflict)

Retornado quando o nickname já está em uso.

```json
{
  "message": "Nickname already in use"
}
```

---

### 2. Login

Autentica um usuário existente.

- **URL:** `/login`
- **Método:** `POST`
- **Content-Type:** `application/json`

#### Corpo da Requisição

```json
{
  "nickname": "usuario_exemplo",
  "password": "senha_segura_123"
}
```

#### Resposta de Sucesso (200 OK)

```json
{
  "accountId": "uuid-da-conta"
}
```

#### Resposta de Erro (401 Unauthorized)

Retornado quando as credenciais são inválidas.

```json
// Sem corpo (ou corpo de erro padrão do Spring)
```

