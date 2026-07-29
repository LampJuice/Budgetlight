# BudgetLight

BudgetLight — приложение для планирования личного бюджета, контроля расходов и управления личными финансами.

Основная идея приложения — помочь пользователю не только учитывать фактические расходы, но и заранее планировать бюджет, устанавливать лимиты по категориям и контролировать выполнение финансового плана.

Проект разрабатывается на Kotlin с использованием современных Android-технологий и архитектурных подходов.

---

# Этап 1 — подготовка архитектуры и слоя данных

На первом этапе была подготовлена базовая архитектура приложения.

Выполнено:

* создана структура проекта с разделением на слои Clean Architecture;
* добавлены основные domain-модели;
* реализована локальная база данных через Room;
* настроена Dependency Injection через Hilt;
* реализованы Repository интерфейсы и их реализации;
* добавлены mapper'ы Entity ↔ Domain;
* настроена работа с Kotlin Coroutines и Flow;
* реализована инициализация пользователя при первом запуске;
* добавлен базовый жизненный цикл приложения;
* реализован экран загрузки приложения.

---

# Этап 2 — бизнес-логика и состояние приложения

На втором этапе реализована связь между слоями приложения.

Выполнено:

* добавлен UseCase слой;
* реализована работа через бизнес-логику приложения;
* добавлена автоматическая инициализация пользователя;
* реализовано получение данных из Room через Flow;
* создано состояние экранов через StateFlow;
* добавлен AppViewModel;
* добавлена HomeViewModel;
* реализован расчёт текущего баланса;
* подготовлено получение транзакций из локальной базы данных.

---

# Этап 3 — Home Dashboard UI

На третьем этапе главный экран был переработан из обычного экрана учёта операций в полноценный финансовый дашборд.

Основная концепция:

```
План бюджета
      ↓
Контроль категорий
      ↓
Фактические расходы
      ↓
Анализ результата
```

Выполнено:

* обновлён дизайн HomeScreen;
* создана компонентная структура Jetpack Compose UI;
* добавлен переиспользуемый BudgetScaffold;
* реализована карточка бюджета месяца;
* добавлено отображение категорий бюджета;
* создан блок последних операций;
* переработан дизайн TransactionItem;
* добавлен Floating Action Button для будущего добавления операций.

Структура HomeScreen:

```
HomeScreen

 ├── GreetingSection
 │
 ├── BudgetSummaryCard
 │
 ├── CategorySection
 │       └── CategoryBudgetItem
 │
 └── RecentTransactionsSection
         └── TransactionItem
```

---

# Используемые технологии

* Kotlin
* Jetpack Compose
* Material 3
* Room Database
* Hilt Dependency Injection
* Kotlin Coroutines
* Flow
* StateFlow
* KSP
* Detekt
* Spotless
* ktlint
* Android Lint
* Gradle Version Catalog

---

# Архитектура проекта

Проект построен на принципах Clean Architecture:

```
ui
 ↓
domain
 ↓
data
```

Каждый слой имеет собственную ответственность и не зависит от деталей реализации нижних уровней.

---

# UI слой

Отвечает за отображение данных и управление состоянием интерфейса.

Используются:

* Jetpack Compose;
* ViewModel;
* StateFlow;
* UI-модели;
* переиспользуемые Compose-компоненты.

Основные элементы:

```
ui
├── app
│   ├── AppState
│   └── AppViewModel
│
├── home
│   ├── HomeScreen
│   ├── HomeViewModel
│   ├── HomeState
│   │
│   ├── components
│   │   ├── GreetingSection
│   │   ├── BudgetSummaryCard
│   │   ├── CategorySection
│   │   ├── CategoryBudgetItem
│   │   ├── RecentTransactionsSection
│   │   └── TransactionItem
│   │
│   └── model
│       ├── BudgetSummaryUi
│       ├── CategoryBudgetUi
│       └── TransactionUi
```

---

# Domain слой

Содержит бизнес-логику приложения, модели данных и контракты.

Модели:

* User
* Account
* Transaction
* TransactionType
* Balance

Repository интерфейсы:

* UserRepository
* AccountRepository
* TransactionRepository

UseCase:

* InitializeUserUseCase
* AddAccountUseCase
* AddTransactionUseCase
* ObserveAccountsUseCase
* ObserveTransactionsUseCase
* ObserveCurrentAccountUseCase
* CalculateBalanceUseCase

Domain слой не зависит от реализации хранения данных.

---

# Data слой

Отвечает за работу с данными и локальным хранилищем.

Используется:

* Room Database;
* DAO;
* Entity;
* Repository implementations;
* Mapper слой.

---

# Room Database

Созданы Entity:

* UserEntity
* AccountEntity
* TransactionEntity

DAO:

* UserDao
* AccountDao
* TransactionDao

Настроены:

* Room Database;
* TypeConverter для LocalDate;
* ForeignKey связи;
* индексы.

Текущая структура данных:

```
User
 |
 └── Account
        |
        └── Transaction
```

В дальнейшем структура будет расширена для планирования бюджета:

```
Budget
 |
 └── BudgetCategory
          |
          └── Category
```

---

# Repository слой

Реализованы:

* UserRepositoryImpl
* AccountRepositoryImpl
* TransactionRepositoryImpl

Поддерживаются операции:

* создание пользователя;
* получение пользователя;
* получение счетов;
* получение транзакций;
* добавление счетов;
* добавление транзакций;
* удаление транзакций.

---

# Dependency Injection

Используется Hilt.

Модули:

```
di
├── DatabaseModule
└── RepositoryModule
```

Через Hilt предоставляются:

* Room Database;
* DAO;
* Repository реализации;
* зависимости приложения.

---

# Application Flow

Запуск приложения:

```
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
```

Добавлен экран загрузки:

* LoadingScreen;
* управление состоянием через StateFlow;
* ожидание инициализации данных перед открытием интерфейса.

---

# Home Flow

Текущая логика HomeScreen:

```
HomeScreen

      ↓

HomeViewModel

      ↓

UseCases

      ↓

Repository

      ↓

Room Database
```

HomeViewModel:

* получает данные приложения;
* хранит состояние через StateFlow;
* рассчитывает текущий баланс;
* подготавливает UI-модели для Compose.

---

# Инструменты качества кода

## Detekt

Используется для:

* статического анализа Kotlin-кода;
* проверки сложности методов;
* поиска потенциальных проблем;
* контроля качества кода.

---

## Spotless + ktlint

Используются для автоматического форматирования и проверки стиля Kotlin-кода.

Возможности:

* единый формат проекта;
* контроль импортов;
* проверка форматирования;
* поддержание единого code style.

Команды:

Форматирование:

```bash
./gradlew format
```

---

## Android Lint

Используется для проверки:

* Android API;
* ресурсов;
* Manifest;
* Compose рекомендаций;
* Android-specific проблем.

---

# Проверка проекта

Перед коммитом выполняются:

```bash
./gradlew format
./gradlew verify
```

Где:

```
format
 ↓
spotlessApply


verify
 ↓
spotlessCheck
detekt
lintDebug
build
```

---

# Текущее состояние проекта

Реализовано:

✅ Clean Architecture
✅ Room Database
✅ Hilt Dependency Injection
✅ Repository слой
✅ Domain слой
✅ UseCase слой
✅ Coroutines + Flow
✅ StateFlow
✅ Инициализация пользователя
✅ LoadingScreen
✅ AppViewModel
✅ HomeViewModel
✅ Расчёт баланса
✅ Новый Home Dashboard UI
✅ BudgetSummaryCard
✅ CategorySection
✅ CategoryBudgetItem
✅ RecentTransactionsSection
✅ Обновлённый TransactionItem
✅ BudgetScaffold
✅ Floating Action Button
✅ Detekt
✅ Spotless + ktlint
✅ Android Lint
✅ Gradle verification tasks
✅ Проект успешно собирается

---

# Следующие этапы разработки

План дальнейшего развития:

1. Подключение HomeScreen к реальным данным.
2. Создание сущностей:

    * Category;
    * Budget;
    * BudgetCategory.
3. Реализация планирования бюджета на месяц.
4. Добавление лимитов расходов по категориям.
5. Экран управления бюджетом.
6. Экран счетов пользователя.
7. Полноценный экран транзакций.
8. Добавление фильтрации и поиска операций.
9. Аналитика расходов.
10. Прогнозирование выполнения бюджета.
11. Улучшение UX и добавление Material 3 Motion.
