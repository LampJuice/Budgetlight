# BudgetLight 💎

**BudgetLight** — приложение для управления личными финансами: планирование бюджета, контроль расходов и доходов, категории, лимиты и аналитика.

Проект развивается как клиент-серверная система и состоит из Android-приложения и собственного backend API.

---

## Архитектура

Проект построен на **Feature-based Clean Architecture** с разделением ответственности между слоями.

```text
BudgetLight
│
├── Android
│   ├── feature:auth
│   ├── feature:launcher
│   └── feature:budget
│
└── Backend
    ├── Ktor
    ├── PostgreSQL
    ├── Exposed
    ├── Koin
    ├── Argon2id
    └── JWT
```

### Android

Каждая feature разделена на:

```text
UI
 ↓
Domain
 ↓
Data
```

**UI**

* Jetpack Compose
* Material 3
* StateFlow
* Navigation Compose

**Domain**

* Use Cases
* Repository interfaces
* Domain models

**Data**

* Room
* DAO
* Repository implementations
* Data mappers
* DataStore

Dependency Injection выполняется с помощью **Hilt**.

### Backend

Backend реализован на **Kotlin + Ktor**.

```text
Presentation
     ↓
Service
     ↓
Domain
     ↓
Repository
     ↓
PostgreSQL
```

Для Dependency Injection используется **Koin**.

---

## Authentication

Авторизация вынесена в отдельную `auth` feature и уже реализована на backend.

### Регистрация

```text
Android
   │
   │ POST /auth/register
   ▼
Ktor
   │
   ▼
RegisterUserUseCase
   │
   ▼
Argon2id
   │
   ▼
UserRepository
   │
   ▼
PostgreSQL
```

Пароль передаётся backend через защищённое HTTPS-соединение и **хешируется Argon2id на сервере**.

В PostgreSQL хранится только password hash.

### Логин

```text
Android
   │
   │ POST /auth/login
   ▼
LoginUserUseCase
   │
   ▼
Argon2id.verify()
   │
   ▼
JwtService
   │
   ▼
JWT
   │
   ▼
Android
```

После успешной аутентификации backend возвращает JWT.

Для защищённых запросов используется:

```http
Authorization: Bearer <JWT>
```

### Проверка JWT

Backend использует Ktor Authentication и проверяет:

* подпись JWT;
* issuer;
* audience;
* expiration;
* наличие `userId`;
* наличие `email`.

Тестовый защищённый endpoint:

```http
GET /auth/me
```

---

## API

На текущем этапе реализованы:

| Method | Endpoint         | Description                              |
| ------ | ---------------- | ---------------------------------------- |
| GET    | `/health`        | Проверка доступности backend             |
| POST   | `/auth/register` | Регистрация пользователя                 |
| POST   | `/auth/login`    | Аутентификация и выдача JWT              |
| GET    | `/auth/me`       | Проверка JWT и получение данных из token |

---

## Технологический стек

### Android

* Kotlin 2.2.10
* Jetpack Compose
* Material 3
* Coroutines / Flow
* Hilt / Dagger
* Room
* DataStore
* Navigation Compose
* Detekt

### Backend

* Kotlin 2.4.0
* Ktor 3.5.2
* PostgreSQL
* Exposed
* HikariCP
* Koin 4.1.1
* Password4j 1.8.4
* Argon2id
* JWT / Auth0 Java JWT
* Kotlinx Serialization

### Infrastructure

* Docker
* PostgreSQL

---

## Тестирование

Проект покрывается несколькими уровнями тестов.

### Unit tests

Проверяются:

* `RegisterUserUseCase`
* `LoginUserUseCase`
* `PasswordHasher`
* `JwtService`

### Repository integration tests

Проверяется реальная работа с PostgreSQL:

* создание пользователя;
* поиск по email;
* отсутствие пользователя;
* уникальность email;
* очистка тестовых данных.

### HTTP integration tests

Через `testApplication` проверяются:

* `/health`;
* регистрация;
* duplicate email;
* сохранение password hash;
* успешный login;
* неправильный пароль;
* неизвестный email;
* JWT authentication;
* защищённый `/auth/me`;
* invalid signature;
* invalid issuer;
* invalid audience;
* expired JWT.

Тесты используют реальный PostgreSQL, а тестовые данные очищаются после выполнения.

Запуск:

```bash
./gradlew test
```

Сборка:

```bash
./gradlew build
```

---

## Безопасность

### Пароли

Пароли не хранятся в открытом виде.

Используется:

```text
Argon2id
```

с параметрами, настроенными в backend.

### JWT

JWT используется только после успешной аутентификации пользователя.

Секрет JWT не должен храниться в исходном коде production-окружения. Он передаётся через environment variable:

```text
JWT_SECRET
```

Для локальной разработки используется dev secret из `application.yaml`.

---

## Структура проекта

```text
BudgetLight
│
├── app / Android
│   ├── feature
│   │   ├── auth
│   │   ├── launcher
│   │   └── budget
│   └── ...
│
└── backend
    └── src
        ├── main
        │   └── kotlin
        │       ├── com.lampjuice
        │       │   ├── database
        │       │   ├── di
        │       │   └── feature
        │       │       └── auth
        │       ├── Routing.kt
        │       ├── Serialization.kt
        │       └── main.kt
        │
        └── test
            └── kotlin
                └── com.lampjuice
```

---

## Текущий статус

### Android

* ✅ Feature-based архитектура
* ✅ Clean Architecture
* ✅ MVVM
* ✅ Jetpack Compose
* ✅ Hilt
* ✅ Room
* ✅ Coroutines / Flow
* 🚧 Интеграция с backend authentication

### Backend

* ✅ Ktor application
* ✅ PostgreSQL
* ✅ Exposed
* ✅ HikariCP
* ✅ Koin
* ✅ User repository
* ✅ Registration
* ✅ Login
* ✅ Argon2id password hashing
* ✅ JWT generation
* ✅ JWT validation
* ✅ Protected `/auth/me`
* ✅ Unit tests
* ✅ Integration tests

### Следующий этап

```text
Android Auth
    ↓
HTTP client
    ↓
POST /auth/register
POST /auth/login
    ↓
JWT storage
    ↓
Authorization header
    ↓
Protected API
```

После интеграции authentication следующим этапом будет подключение финансовой части BudgetLight к backend API.
