# Прогноз погоды

Мобильное приложение, позволяющее узнать погоду на сегодня и на ближайшие 3 дня. Приложение разработано на языке Kotlin с использованием Jetpack Compose верстки для пользовательского интерфейса, Room для работы с базой данных, Volley для работы с сетью. 


## **Функциональные требования**

### Карточка основной информации

В верхней части экрана расположена карточка с:
- основной информацией о погоде на текущий момент(по дефолту: ваша геолакации)
- время последней загрузки данных о погоде
- кнопка, по нажатию которой открывается диалоговое окно, где можно ввести интересующий город
- кнопка для синхронизации данных 

### Фрагмент "По часам"

На фрагменте отображается краткая информация по часам выбранного дня(по дефолту текущая дата)

### Фрагмент "По дням"

На фрагменте отображается краткая информация 3-ёх ближайших дней(учитывая текущую дату). По нажатию на карточку меняется информация в карточке основной информацию, а также на фрагменте "По часам" отображается погода по часам выбранной даты.


### Скриншоты

<img src="./screenshots/screenshot_3.jpg" alt="ScreenShot 3" width="400"/>

<img src="./screenshots/screenshot_2.jpg" alt="ScreenShot 2" width="400"/>

<img src="./screenshots/screenshot_1.jpg" alt="ScreenShot 1" width="400"/>


## Стек
 - Язык: kotlin
 - Работа с базой данных: Room
 - Работа с сетью: Volley
 - View: Jetpack Compose

## **Инструкция по запуску**

1. Клонируйте репозиторий:
   ```bash
   git clone <URL-репозитория>
2. Откройте проект в Android Studio.
3. Соберите и запустите приложение на эмуляторе или устройстве Android.
