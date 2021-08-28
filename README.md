![](media/QuizZon_Head.png)

# **QuizZon**

**Kuberam** is an app to manage your transactions.

# Application Install

***You can Install Kuberam app from Play Store 👇***

[![Kuberam](https://img.shields.io/badge/Kuberam✅-APK-red.svg?style=for-the-badge&logo=googleplay)](https://play.google.com/store/apps/details?id=com.kuberam.android)

## Setup
Clone the repository on your machine. Open the project on your IDE and connect it to firebase and Auth0 and everything will be setup

- Add your firebase json class in app directory
- Add your Auth0 Credentital in

## About

 It uses firebase and Auth0 as it's backend. It uses Auth0 Authentication for auth, Firebase Crashlytics for crash reporting and Firestore as its database.

- Fully functionable.
- Clean and Simple Material UI.

### Insights into the app 🔎

## Features

-[Auth0 Login]() - Auth0 universal login
-[Biometric Lock]() - Use Biometric/Default Mobile lock
-[Day & Night]() - Day and Night Theme.
-[Add Transaction]() - Add Transaction.
-[Create Category]() - Create Category
-[Pie Chart]() - Pie Chart of Income and Expense data
-[Firestore Database]() - Firestore database for data
## 📸 Screenshots

||||
|:----------------------------------------:|:-----------------------------------------:|:-----------------------------------------: |
| ![](media/1.png) | ![](media/2.png) | ![](media/3.png) |
| ![](media/4.png)  | ![](media/5.png) | ![](media/6.png)    |
| ![](media/7.png) | ![](media/8.png)    | ![](media/9.png)      |
|![](media/10.png)  |![](media/11.png)  |![](media/12.png)      |
|![](media/13.png)  |![](media/14.png)  |![](media/15.png)      |

## Built With 🛠
- [Auth0](https://auth0.com/) -  Auth0 is an easy to implement, adaptable authentication and authorization platform.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose is Android’s modern toolkit for building native UI.
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [MutableState](https://developer.android.com/jetpack/compose/state) - MutableState is an alternative to using LiveData or Flow . Compose does not observe any changes to this object by default and therefore no recomposition will happen.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Firebase](https://firebase.google.com)
    - Firebase Firestore - To save data in firebase firestore database
    - Firebase Crashlytics - To report app crashes
    - Firebase Analytics - To report app crashes
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

# Package Structure

    com.kuberam.android     # Root Package
    .
    ├── Components          # For data handling.
    |
    ├── data
    |   ├── local           # Datastore and its related classes
    |   ├── model           # Firebase, HarperDB and their relative classes
    │   ├── remote          # Model data classes, both remote and local entities
    |
    ├── di                  # Dependency Injection
    │   └── module          # DI Modules
    |
    ├── navigation
    |
    ├── service
    |
    ├── ui
    |   ├── theme           # Manage Themes: Color, Shape, Theme and Type.
    │   ├── view            # All Views: About Screen, All Transaction Screen, Auth Screen, Dashboard Screen, OnBoard Screen, Profile Screen, Splash Screen.
    │   ├── viewmodel       # Viewmodel of app
    |
    |
    └── utils               # Utility Classes: Constant, Interfaces etc.


## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

## Contribute 🤝

If you want to contribute to this library, you're always welcome!

## Contact 📩

Have an project? DM us at 👇<br>
[![Mail](https://img.shields.io/badge/Gmail-green.svg?style=for-the-badge&logo=gmail)](mailto://rohitjakhar940@gmail.com)

[![LinkedIn](https://img.shields.io/badge/LinkedIn-red.svg?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/rohitjakhar0/)


## Donation 💰

If this project help you reduce time to develop, you can give me a cup of coffee :)

<a href="https://www.buymeacoffee.com/rohitjakhar" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/yellow_img.png" alt="Buy Me A Coffee" style="height: 41px !important;width: 174px !important;box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;-webkit-box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;" ></a>

<br>