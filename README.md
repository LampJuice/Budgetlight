# BudgetLight

BudgetLight — Android-приложение для планирования личного бюджета, контроля расходов и управления финансами.

Приложение позволяет планировать месячный бюджет, устанавливать лимиты по категориям и отслеживать фактические расходы.

Проект разрабатывается на Kotlin с использованием Jetpack Compose и Clean Architecture.

## Архитектура

Проект построен по принципам Clean Architecture с разделением на три основных слоя:

```text
ui
 ↓
domain
 ↓
data
```

### UI

UI реализован на Jetpack Compose. Состояние экранов управляется через ViewModel и StateFlow.

Основной экран Home Dashboard включает:

```text
HomeScreen
├── GreetingSection
├── BudgetSummaryCard
├── CategorySection
│   └── CategoryBudgetItem
└── RecentTransactionSection
    └── TransactionItem
```

Также используется переиспользуемый `BudgetScaffold` и Floating Action Button для добавления операций.

### Domain

Domain-слой содержит бизнес-модели, Repository-контракты и UseCase.

Основные сущности:

* User
* Account
* Transaction
* Category
* Budget
* BudgetCategory

Основные UseCase:

* InitializeUserUseCase
* CalculateBalanceUseCase
* ObserveTransactionsUseCase
* ObserveTransactionInfoUseCase
* ObserveCurrentAccountUseCase
* ObserveCurrentBudgetUseCase
* ObserveBudgetCategoryInfoUseCase
* AddAccountUseCase
* AddTransactionUseCase

### Data

Для локального хранения используется Room.

Реализованы:

* Entity;
* DAO;
* Repository implementations;
* Entity ↔ Domain mapper'ы;
* ForeignKey связи;
* индексы;
* TypeConverter для `LocalDate`.

Текущая структура данных:

```text
User
 └── Account
      └── Transaction

Budget
 └── BudgetCategory
      └── Category
```

На текущем этапе пользователь работает с одним основным счётом. Архитектура `Account` при этом уже подготовлена для дальнейшей поддержки нескольких счетов.

## Инициализация приложения

При запуске приложение проходит базовую инициализацию:

```text
Application start
       ↓
AppViewModel
       ↓
InitializeUserUseCase
       ↓
SeedApplicationDataUseCase
       ↓
AppState.Ready
       ↓
Navigation
```

Для отображения состояния загрузки используется отдельный `LoadingScreen`.

## Home Dashboard

Главный экран отображает:

* приветствие пользователя;
* текущий бюджет;
* сумму расходов;
* остаток бюджета;
* прогресс выполнения бюджета;
* лимиты по категориям;
* фактические расходы по категориям;
* последние операции.

Категории используют собственные Material Icons, а операции отображают иконку соответствующей категории.

Данные HomeScreen поступают из Room через `Flow` и преобразуются в UI-модели перед отображением.

## Технологии

* Kotlin
* Jetpack Compose
* Material 3
* Room
* Hilt
* Coroutines
* Flow
* StateFlow
* KSP
* Navigation Compose
* Detekt
* Spotless
* ktlint
* Android Lint
* Gradle Version Catalog

## Качество кода

Для контроля качества используются Detekt, Spotless + ktlint и Android Lint.

Перед коммитом:

```bash
./gradlew format
./gradlew verify
```

`verify` выполняет проверки форматирования, статический анализ, Android Lint и сборку проекта.

## Текущее состояние

На данный момент реализованы:

* Clean Architecture;
* Room Database;
* Hilt DI;
* Repository и UseCase слои;
* пользователь инициализируется автоматически;
* один основной счёт;
* категории и категории бюджета;
* месячный бюджет;
* расчёт баланса;
* Home Dashboard;
* отображение бюджета и категорий;
* отображение последних операций;
* иконки категорий;
* LoadingScreen;
* AppViewModel;
* HomeViewModel;
* Floating Action Button;
* инструменты проверки и форматирования кода.

Следующий этап разработки — реализация полноценного сценария добавления операции через кнопку `+`.
