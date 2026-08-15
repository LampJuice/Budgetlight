# BudgetLight

BudgetLight — современное Android-приложение для планирования личного бюджета, контроля расходов и управления финансами.

Приложение построено на принципах реактивного программирования: любые изменения в базе данных мгновенно отображаются в интерфейсе.

## Архитектура

Проект строго следует принципам **Clean Architecture**:

ui (Compose + ViewModel + Mappers)
↓
domain (Models + UseCases + Repository Interfaces)
↓
data (Room + DAOs + Repository Implementations + Mappers)

### Навигация
В приложении реализована типичная для Compose навигация через `AppNavHost`:
*   **Home**: Главный дашборд с обзором финансов.
*   **Transactions**: Полный список операций с фильтрацией и возможностью удаления (Swipe-to-Delete).
*   **Add Transaction**: Экран создания новых доходов и расходов.

## Основной функционал

### 1. Планирование бюджета
*   **Месячные циклы**: Бюджет привязан к конкретному месяцу и году. При переходе на новый месяц приложение автоматически подготавливает структуру бюджета.
*   **Лимиты по категориям**: Возможность устанавливать индивидуальные лимиты для каждой расходной категории.
*   **Архивация**: Поддержка архивации категорий, которые больше не используются, с сохранением исторической точности в прошлых периодах.

### 2. Учет операций
*   **Типы транзакций**: Поддержка Доходов и Расходов.
*   **Детализация**: Каждая операция имеет название, сумму, дату и категорию.
*   **Гибкий ввод**: Удобный выбор даты через календарь и выбор категории из выпадающего списка.

### 3. Аналитика (Home Dashboard)
*   Визуальный индикатор прогресса (Spent vs Limit).
*   Автоматический расчет остатка средств.
*   Секция последних операций для быстрого контроля.

## Технический стек

*   **UI**: Jetpack Compose, Material 3 (Material You).
*   **DI**: Hilt (Dagger).
*   **Database**: Room (с поддержкой Flow, Foreign Keys и индексов).
*   **Architecture Components**: ViewModel, Navigation Compose, Lifecycle KTX.
*   **Date & Time**: Java Time API (LocalDate) с TypeConverters для Room.
*   **Code Quality**: Detekt, Spotless, ktlint, Android Lint.

## Структура Domain-слоя

Основные UseCase-ы, реализующие бизнес-логику:

*   **Управление данными**: `InitializeUserUseCase`, `SeedApplicationDataUseCase`, `SeedDatabaseUseCase`.
*   **Наблюдение (Flow)**: `ObserveHomeDataUseCase`, `ObserveTransactionsUseCase`, `ObserveCurrentBudgetUseCase`, `ObserveBudgetCategoryInfoUseCase`.
*   **Действия**: `AddTransactionUseCase`, `DeleteTransactionUseCase`, `SaveCurrentBudgetUseCase`, `AddCategoryUseCase`, `ArchiveCategoryUseCase`, `UpdateBudgetCategoryLimitUseCase`.

## Качество кода

Проект поддерживает высокие стандарты оформления кода. Перед внесением изменений рекомендуется выполнять проверку:

./gradlew format  # Автоматическое форматирование (Spotless + ktlint)
./gradlew verify  # Полная проверка (Lint + Detekt + Тесты)

## Текущее состояние

✅ **Реализовано:**
*   Полная структура Clean Architecture и DI (Hilt).
*   Реактивная база данных (Room + Flow).
*   Главный экран (Home Dashboard) с расчетом прогресса.
*   Экран всех транзакций с группировкой по датам.
*   Экран добавления операций с валидацией форм.
*   Диалоги редактирования лимитов и добавления категорий.
*   Механика архивации категорий.
*   Локализация через ресурсы (strings.xml).
