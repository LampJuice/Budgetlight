# BudgetLight 💎

**BudgetLight** — Android-приложение для управления личными финансами: планирование бюджета, контроль расходов и доходов, категории, лимиты и аналитика.

Проект развивается как клиент-серверная система и состоит из Android-приложения и собственного backend API на Kotlin/Ktor.

---

## Архитектура

Проект построен на **Feature-based Clean Architecture + MVVM**.

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

Каждая feature разделена на слои:

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
* Remote API / DTO
* Data mappers
* DataStore
* Ktor Client

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

Авторизация вынесена в отдельную `auth` feature и работает через backend.

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

Android передаёт backend логин, имя и пароль. Пароль хешируется **на сервере** с использованием Argon2id. В PostgreSQL хранится только hash пароля.

После успешной регистрации backend возвращает:

```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "User",
  "token": "<JWT>"
}
```

### Логин

```text
Android
   │
   │ POST /auth/login
   ▼
Ktor
   │
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

После успешной аутентификации backend возвращает пользователя и JWT.

Android сохраняет локальную сессию в **DataStore**:

```text
userId
accessToken
```

Локальный `User` хранится в Room и используется приложением для связи с локальными финансовыми данными.

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
* `userId`;
* `email`;
* `name`.

Защищённый endpoint:

```http
GET /auth/me
```

---

## API

На текущем этапе реализованы:

| Method | Endpoint         | Description                                           |
| ------ | ---------------- | ----------------------------------------------------- |
| GET    | `/health`        | Проверка доступности backend                          |
| POST   | `/auth/register` | Регистрация и выдача JWT                              |
| POST   | `/auth/login`    | Аутентификация и выдача JWT                           |
| GET    | `/auth/me`       | Проверка JWT и получение данных текущего пользователя |

### Register

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "email": "user@example.com",
  "name": "User",
  "password": "secret"
}
```

Успешный ответ: `201 Created`.

### Login

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "user@example.com",
  "password": "secret"
}
```

Успешный ответ: `200 OK` и JWT.

---

## Локальное хранение и backend

На текущем этапе authentication уже работает через backend, но **финансовые данные приложения пока хранятся локально в Room**.

Поэтому пользователь может войти с другого устройства, однако транзакции, бюджеты, категории и другие локальные данные ещё не синхронизируются автоматически между устройствами.

Планируемая схема:


```text
                PostgreSQL
                    │
                Ktor API
                    │
              REST + JWT
                    │
          ┌─────────┴─────────┐
          │                   │
      Device A             Device B
          │                   │
        Room                Room
          │                   │
          └────── sync ──────┘
```

В дальнейшем backend станет источником истины для финансовых данных, а Room — локальным кешем/offline storage.

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
* Ktor Client
* Kotlinx Serialization
* Detekt
* Spotless / ktlint

### Backend

* Kotlin 2.4.0
* Ktor 3.5.2
* PostgreSQL 18
* Exposed
* HikariCP
* Koin 4.1.1
* Password4j 1.8.4
* Argon2id
* Auth0 Java JWT
* Kotlinx Serialization

### Infrastructure

* Docker
* Docker Compose
* PostgreSQL

---

## Тестирование

Backend покрыт unit- и integration-тестами.

### Unit tests

Проверяются:

* `RegisterUserUseCase`
* `LoginUserUseCase`
* `PasswordHasher`
* `JwtService`

### Repository integration tests

Проверяется работа с реальным PostgreSQL:

* создание пользователя;
* поиск по email;
* отсутствие пользователя;
* уникальность email;
* очистка тестовых данных.

### HTTP integration tests

Через Ktor `testApplication` проверяются:

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

Backend tests используют реальный PostgreSQL в Docker и очищают тестовые данные после выполнения.

Текущий backend checkpoint: **29 тестов проходят успешно**.

Запуск тестов:

```bash
./gradlew test
```

Сборка backend:

```bash
./gradlew build
```

Android-проверки:

```bash
./gradlew format
./gradlew verify
```

---

## Безопасность

### Пароли

Пароли не хранятся в открытом виде.

Используется:

```text
Argon2id
```

Пароль хешируется только на backend.

### JWT

JWT создаётся backend после успешной аутентификации и используется для защищённых API-запросов.

Секрет JWT не должен храниться в исходном коде production-окружения. Для production он передаётся через environment variable:

```text
JWT_SECRET
```

Для локальной разработки используется dev secret из `application.yaml`.

### HTTPS

В локальной разработке Android Emulator обращается к backend по HTTP через `10.0.2.2:8080`.

В production планируется использовать HTTPS.

---

## Room migrations

Локальная база Android развивается через явные Room migrations.

Текущая версия `BudgetDatabase` — **5**.

Основные изменения:

```text
3 → 4
    уникальный индекс budgets(userId, year, month)

4 → 5
    удаление passwordHash из локальной users таблицы
```

После переноса authentication на backend Android больше не хранит пароль или его hash в Room.

---

## Структура проекта

```text
BudgetLight
│
├── app / Android
│   ├── feature
│   │   ├── auth
│   │   │   ├── data
│   │   │   │   ├── local
│   │   │   │   ├── remote
│   │   │   │   └── repository
│   │   │   ├── domain
│   │   │   │   ├── model
│   │   │   │   ├── repository
│   │   │   │   ├── session
│   │   │   │   └── usecase
│   │   │   └── ui
│   │   ├── launcher
│   │   └── budget
│   ├── database
│   ├── di
│   └── navigation
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
* ✅ Room migrations
* ✅ Coroutines / Flow
* ✅ DataStore session
* ✅ Ktor Client
* ✅ Kotlinx Serialization
* ✅ Backend registration
* ✅ Backend login
* ✅ JWT session storage
* ✅ Logout
* ✅ Session restoration after app restart
* ✅ `format` / `verify` проходят успешно

### Backend

* ✅ Ktor application
* ✅ PostgreSQL 18
* ✅ Docker Compose
* ✅ Exposed
* ✅ HikariCP
* ✅ Koin
* ✅ User repository
* ✅ Registration
* ✅ Login
* ✅ User name
* ✅ Argon2id password hashing
* ✅ JWT generation
* ✅ JWT validation
* ✅ Protected `/auth/me`
* ✅ Unit tests
* ✅ Repository integration tests
* ✅ HTTP integration tests
* ✅ 29 tests green

### Следующий этап

Главная следующая задача — сделать финансовые данные серверными и синхронизируемыми между устройствами:

```text
Account
Category
Budget
BudgetCategory
Transaction
        ↓
Ktor API
        ↓
PostgreSQL
        ↓
Android sync
        ↓
Room
```

После этого можно будет реализовать полноценную multi-device синхронизацию и offline-first поведение.
