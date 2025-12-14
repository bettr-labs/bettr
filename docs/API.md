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

---

### 3. Tipos de Dreams

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

### 4. Criar Dreams

Cadastra uma lista de sonhos para um usuário.

- **URL:** `/dreams`
- **Método:** `POST`
- **Content-Type:** `application/json`

#### Corpo da Requisição

```json
[
  {
    "accountId": "uuid-da-conta",
    "title": "Minha casa própria",
    "targetAmount": 500000.00,
    "deadline": "2030-01-01"
  },
  {
    "accountId": "uuid-da-conta",
    "title": "Viagem para Paris",
    "targetAmount": 15000.00,
    "deadline": "2025-12-31"
  }
]
```

#### Resposta de Sucesso (200 OK)

Retorna 200 OK (sem corpo ou vazio).

---

### 5. Buscar Dreams de um Usuário

Busca todos os sonhos de um usuário.

- **URL:** `/dreams?account_id={uuid-da-conta}`
- **Método:** `GET`

#### Resposta de Sucesso (200 OK)

```json
[
  {
    "dreamId": "uuid-do-sonho-1",
    "accountId": "uuid-da-conta",
    "title": "Minha casa própria",
    "targetAmount": 500000.00,
    "deadline": "2030-01-01"
  }
  // ... outros sonhos
]
```

#### Resposta de Erro (400 Bad Request)

Se `account_id` não for informado.

---

### 6. Buscar Dream Específico

Busca um sonho específico de um usuário.

- **URL:** `/dreams?account_id={uuid-da-conta}&dreamId={uuid-do-sonho}`
- **Método:** `GET`

#### Resposta de Sucesso (200 OK)

```json
{
  "dreamId": "uuid-do-sonho",
  "accountId": "uuid-da-conta",
  "title": "Minha casa própria",
  "targetAmount": 500000.00,
  "deadline": "2030-01-01"
}
```

#### Resposta de Erro (404 Not Found)

Se o sonho não for encontrado para aquela conta.
