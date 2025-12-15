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

### 2. Buscar Conta

Retorna os dados de uma conta.

- **URL:** `/accounts/{accountId}`
- **Método:** `GET`

#### Resposta de Sucesso (200 OK)

```json
{
  "id": "uuid-da-conta",
  "nickname": "usuario_exemplo",
  "createdAt": "2023-10-27T10:00:00Z",
  "status": "ACTIVE"
}
```

#### Resposta de Erro (404 Not Found)

Se a conta não for encontrada.

---

### 3. Atualizar Conta

Atualiza dados da conta (nickname).

- **URL:** `/accounts/{accountId}`
- **Método:** `PATCH`
- **Content-Type:** `application/json`

#### Corpo da Requisição

```json
{
  "nickname": "novo_nickname"
}
```

#### Resposta de Sucesso (200 OK)

Retorna 200 OK (sem corpo ou vazio).

#### Resposta de Erro (404 Not Found)

Se a conta não for encontrada.

---

### 4. Desativar Conta

Desativa uma conta (muda status para INACTIVE).

- **URL:** `/accounts/{accountId}/deactivate`
- **Método:** `PATCH`

#### Resposta de Sucesso (200 OK)

Retorna 200 OK (sem corpo ou vazio).

#### Resposta de Erro (404 Not Found)

Se a conta não for encontrada.

---

### 5. Login

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

Retornado quando as credenciais são inválidas ou a conta está inativa.

**Credenciais Inválidas:**
```json
{
  "reason": "combination of Nickname and password is invalid"
}
```

**Conta Inativa:**
```json
{
  "reason": "account is inactive"
}
```

---

### 6. Tipos de Dreams

Lista os tipos de sonhos disponíveis.

- **URL:** `/dreams-types`
- **Método:** `GET`

#### Resposta de Sucesso (200 OK)

```json
[
  {
    "key": "HOME",
    "label": "Comprar um imóvel",
    "emoji": "house"
  },
  {
    "key": "TRAVEL",
    "label": "Viajar",
    "emoji": "plane"
  }
  // ... outros tipos
]
```

---

### 7. Criar Dreams

Cadastra uma lista de sonhos para uma conta específica. `currentAmount` é inicializado como zero.

- **URL:** `/accounts/{accountId}/dreams`
- **Método:** `POST`
- **Content-Type:** `application/json`

#### Corpo da Requisição

```json
[
  {
    "title": "Minha casa própria",
    "targetAmount": 500000.00,
    "deadline": "2030-01-01"
  },
  {
    "title": "Viagem para Paris",
    "targetAmount": 15000.00,
    "deadline": "2025-12-31"
  }
]
```

#### Resposta de Sucesso (200 OK)

Retorna 200 OK (sem corpo ou vazio).

#### Resposta de Erro (400 Bad Request)

Se a conta informada não existir.

```json
{
  "message": "Account not found"
}
```

---

### 8. Atualizar Dream (Depositar)

Atualiza o valor atual (`currentAmount`) de um sonho específico.

- **URL:** `/accounts/{accountId}/dreams/{dreamId}`
- **Método:** `PATCH`
- **Content-Type:** `application/json`

#### Corpo da Requisição

```json
{
  "currentAmount": 1500.00
}
```

#### Resposta de Sucesso (200 OK)

Retorna 200 OK (sem corpo ou vazio).

#### Resposta de Erro (400 Bad Request)

Se `accountId` ou `dreamId` não forem informados ou não forem UUIDs válidos.

#### Resposta de Erro (404 Not Found)

Se o sonho não for encontrado para aquela conta.

---

### 9. Buscar Dreams de uma Conta

Busca todos os sonhos de uma conta específica.

- **URL:** `/accounts/{accountId}/dreams`
- **Método:** `GET`

#### Resposta de Sucesso (200 OK)

```json
[
  {
    "dreamId": "uuid-do-sonho-1",
    "accountId": "uuid-da-conta",
    "title": "Minha casa própria",
    "targetAmount": 500000.00,
    "currentAmount": 0.00,
    "deadline": "2030-01-01"
  }
  // ... outros sonhos
]
```

#### Resposta de Erro (400 Bad Request)

Se `accountId` não for um UUID válido.

---

### 10. Buscar Dream Específico

Busca um sonho específico de uma conta.

- **URL:** `/accounts/{accountId}/dreams/{dreamId}`
- **Método:** `GET`

#### Resposta de Sucesso (200 OK)

```json
{
  "dreamId": "uuid-do-sonho",
  "accountId": "uuid-da-conta",
  "title": "Minha casa própria",
  "targetAmount": 500000.00,
  "currentAmount": 1500.00,
  "deadline": "2030-01-01"
}
```

#### Resposta de Erro (404 Not Found)

Se o sonho não for encontrado para aquela conta.
