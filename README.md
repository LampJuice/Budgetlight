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
- настроена работа с Kotlin Coroutines и Flow;
- реализован базовый жизненный цикл запуска приложения;
- добавлена инициализация пользователя при старте приложения;
- реализован экран загрузки приложения.

## Используемые технологии

- Kotlin
- Jetpack Compose
- Room Database
- Hilt Dependency Injection
- Kotlin Coroutines
- Flow
- KSP
- Detekt
- Spotless
- ktlint
- Android Lint
- Gradle Version Catalog

## Архитектура проекта

Проект разделён на основные слои:

ui
↓
domain
↓
data

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

UseCase:

- InitializeUserUseCase

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

User
└── Account
└── Transaction

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

Entity → Domain
Domain → Entity

Реализованы mapper'ы:

- UserMapper
- AccountMapper
- TransactionMapper

Mapper слой отвечает за преобразование моделей базы данных в бизнес-модели приложения.

## Dependency Injection

Настроен Hilt.

Добавлены модули:

di
├── DatabaseModule
└── RepositoryModule

Через Hilt предоставляются:

- Room Database;
- DAO;
- Repository implementations;
- зависимости ViewModel.

## Application flow

Добавлена базовая логика запуска приложения:

Application start
↓
AppViewModel
↓
InitializeUserUseCase
↓
UserRepository
↓
Room Database
↓
AppState.Ready
↓
Navigation

Добавлен экран загрузки:

- LoadingScreen;
- управление состоянием приложения через StateFlow;
- ожидание инициализации данных перед открытием основного интерфейса.

## Инструменты качества кода

В проект подключены инструменты автоматического контроля качества кода.

### Detekt

Используется для:

- статического анализа Kotlin-кода;
- проверки сложности методов;
- поиска потенциальных проблем;
- контроля качества кода.

### Spotless + ktlint

Используются для автоматического форматирования Kotlin-кода.

Возможности:

- форматирование отступов;
- удаление лишних импортов;
- исправление стиля кода;
- единый формат проекта.

Команда форматирования:

./gradlew format

### Android Lint

Используется для проверки Android-специфичных проблем:

- ресурсы;
- Manifest;
- Compose рекомендации;
- Android API проверки.

## Проверка проекта

Перед коммитом рекомендуется выполнить:

./gradlew format

После форматирования выполнить проверку:

./gradlew verify

Проверки выполняют:

- статический анализ Kotlin-кода;
- проверку Android Lint;
- сборку проекта.

## Текущее состояние проекта

На текущем этапе реализована базовая инфраструктура приложения:

✅ настроена архитектура проекта
✅ подключена Room Database
✅ реализованы DAO
✅ настроен Hilt
✅ реализован Repository слой
✅ добавлены Entity и Domain модели
✅ реализованы Mapper'ы
✅ добавлен UseCase слой
✅ реализована инициализация приложения
✅ добавлен LoadingScreen
✅ настроен Detekt
✅ настроен Spotless + ktlint
✅ подключен Android Lint
✅ настроен pipeline проверки качества кода
✅ проект успешно собирается

## Следующие этапы разработки

План дальнейшей разработки:

1. Создание HomeScreen с подключением к реальным данным.
2. Создание ViewModel для экранов.
3. Реализация UI состояния через StateFlow.
4. Отображение текущего пользователя и счетов.
5. Отображение списка транзакций.
6. Добавление создания доходов и расходов.
7. Реализация категорий операций.
8. Добавление аналитики бюджета.
