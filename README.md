# Shopping List

## App design and architecture
Project is based on MVVM architecture.Implements "Single Activity - multiple Fragments" navigation pattern.
This App sample will cover all the necessary things you need to know about testing in android.

Android Jetpack Architecture Components used:
- ViewModel
- LiveData
- Room
- Navigation component

Dependency injection:
- Hilt

Kotlin libraries:
- Kotlin Coroutines
- various ktx extensions

Testing:

- 🛠 A collection of unit, integration and e2e tests

UI tests don't rely on using the `mock` flavor to run quickly and hermetically. Instead, they
use Hilt to provide their test versions.

This is done by creating a `CustomTestRunner` that uses an `Application` configured with Hilt. As
per the [Hilt testing documentation](https://developer.android.com/training/dependency-injection/hilt-android),
`@HiltAndroidTest` will automatically create the right Hilt components for tests.

## Credits

Project uses third-party libraries:
- [Retrofit](https://github.com/square/retrofit) for making RESTful API calls
- [Timber](https://github.com/JakeWharton/timber) logging
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) dependency injection
- [Glide](https://github.com/bumptech/glide) image loading library
- [Truth](https://github.com/google/truth) assertions library for unit testing

