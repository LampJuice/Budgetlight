# BudgetLight

BudgetLight — приложение для управления личным бюджетом и контроля финансов.

Проект разрабатывается на Kotlin с использованием современных Android-технологий и архитектурных подходов.

## Этап 1 — подготовка архитектуры и слоя данных

На первом этапе была подготовлена базовая архитектура приложения:

- создана структура проекта с разделением на слои Clean Architecture;
- добавлены domain-модели приложения;
- реализована локальная база данных через Room;
- настроена Dependency Injection через Hilt;
- реализованы Repository интерфейсы и их реализации;
- добавлены mapper'ы для преобразования Entity ↔ Domain;
- настроена работа с Kotlin Coroutines и Flow.

## Используемые технологии

- Kotlin
- Jetpack Compose
- Room Database
- Hilt Dependency Injection
- Kotlin Coroutines
- Flow
- KSP
- Detekt
- Gradle Version Catalog

## Архитектура проекта

Проект разделён на основные слои:

```
ui
↓
domain
↓
data
```

### Domain слой

Содержит бизнес-логику приложения, модели данных и контракты.

Модели:

- User
- Account
- Transaction
- TransactionType

Repository интерфейсы:

- UserRepository
- AccountRepository
- TransactionRepository

Domain слой не зависит от реализации хранения данных.

### Data слой

Отвечает за работу с данными и взаимодействие с локальным хранилищем.

## Room Database

Созданы Entity:

- UserEntity
- AccountEntity
- TransactionEntity

Созданы DAO:

- UserDao
- AccountDao
- TransactionDao

Настроены:

- Room Database;
- TypeConverter для работы с LocalDate;
- внешние ключи между таблицами;
- индексы для связей ForeignKey.

Структура связей:

```
User
 |
 └── Account
        |
        └── Transaction
```

## Repository слой

Созданы реализации Repository:

- UserRepositoryImpl
- AccountRepositoryImpl
- TransactionRepositoryImpl

Реализованы операции:

- получение пользователя;
- получение списка счетов;
- получение списка транзакций;
- добавление счетов;
- добавление транзакций;
- удаление транзакций.

## Mapper слой

Добавлены преобразования между слоями:

```
Entity → Domain
Domain → Entity
```

Реализованы mapper'ы:

- UserMapper
- AccountMapper
- TransactionMapper

Mapper слой отвечает за преобразование моделей базы данных в бизнес-модели приложения.

## Dependency Injection

Настроен Hilt.

Добавлены модули:

```
di
├── DatabaseModule
└── RepositoryModule
```

Через Hilt предоставляются:

- Room Database;
- DAO;
- Repository implementations.

## Инструменты качества кода

Подключен Detekt для статического анализа Kotlin-кода.

Проверки проекта:

```
./gradlew clean
./gradlew detekt
./gradlew build
```

Все проверки проходят успешно.

## Текущее состояние проекта

На текущем этапе реализована базовая инфраструктура приложения:

✅ настроена архитектура проекта
✅ подключена Room Database
✅ реализованы DAO
✅ настроен Hilt
✅ реализован Repository слой
✅ добавлены Entity и Domain модели
✅ реализованы Mapper'ы
✅ настроен Detekt
✅ проект успешно собирается

## Следующие этапы разработки

План дальнейшей разработки:

1. Добавление UseCase слоя.
2. Создание ViewModel для экранов.
3. Реализация UI состояния через StateFlow.
4. Подключение HomeScreen к реальным данным.
5. Добавление создания доходов и расходов.
6. Реализация категорий операций.
7. Добавление аналитики бюджета.
