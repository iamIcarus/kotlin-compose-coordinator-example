# Kotlin Compose Coordinator Pattern Example

This repository demonstrates the use of the Coordinator pattern in a Kotlin project using Jetpack Compose. The Coordinator pattern is employed to manage navigation and screen flows in a decoupled and reusable manner.

## Project Structure

The project is composed of several key interfaces and classes that define the coordinator pattern:

### Interfaces

- **`CoordinatorAction`**: A marker interface that all actions extend.
- **`Coordinator`**: An interface that defines the contract for coordinators responsible for managing screen flows.
- **`RootCoordinator`**: Extends `Coordinator` with the ability to start with a specific action.
- **`NavigationRoute`**: Defines the navigation routes within the application.
- **`Router`**: Defines navigation capabilities for coordinators.

### Sealed Classes

- **`GeneralAction`**: Represents general actions like `Done` and `Cancel`.
- **`AppCoordinatorAction`**: Represents actions specific to the `AppCoordinator`, like `GoToMain`, `GoToLogin`, and `LogOut`.
- **`AuthCoordinatorAction`**: Represents actions specific to the `AuthCoordinator`, like `GoToSettings`, `Authenticated`, and `GoBack`.

### Coordinators

#### AppCoordinator

The `AppCoordinator` is the root coordinator responsible for managing the overall flow of the application, including authentication and the main content.

```kotlin
class AppCoordinator(
    private val navController: NavHostController
) : RootCoordinator {

    private var _authCoordinator: AuthCoordinator? = null
    private var _mainCoordinator: MainCoordinator? = null

    private val authCoordinator: AuthCoordinator
        get() {
            if (_authCoordinator == null) {
                _authCoordinator = AuthCoordinator(navController)
            }
            return _authCoordinator!!
        }

    private val mainCoordinator: MainCoordinator
        get() {
            if (_mainCoordinator == null) {
                _mainCoordinator = MainCoordinator(navController)
            }
            return _mainCoordinator!!
        }

    @Composable
    override fun handle(action: CoordinatorAction) {
        when (action) {
            is GeneralAction.Done -> println("Done action with data: ${action.data}")
            is GeneralAction.Cancel -> println("Cancelled with reason: ${action.reason}")
            is AppCoordinatorAction.GoToMain -> mainCoordinator.start()
            is AppCoordinatorAction.GoToLogin -> authCoordinator.start()
            is AppCoordinatorAction.LogOut -> authCoordinator.start()
            else -> throw IllegalArgumentException("Unsupported action")
        }
    }

    @Composable
    override fun start(action: AppCoordinatorAction) {
        handle(action)
    }
}
```

#### AuthCoordinator

The `AuthCoordinator` manages the flow for authentication, including navigating between the login and settings screens.

```kotlin
class AuthCoordinator(
    private val navController: NavHostController,
    override val initialScreen: NavigationRoute = AuthNavigationRoute.LOGIN
) : Coordinator, Router {

    override fun handle(action: CoordinatorAction) {
        when (action) {
            is GeneralAction.Done -> println("Done action with data: ${action.data}")
            is GeneralAction.Cancel -> println("Cancelled with reason: ${action.reason}")
            is AuthCoordinatorAction.GoToSettings -> navigate(AuthNavigationRoute.SETTINGS)
            is AuthCoordinatorAction.GoBack -> navController.popBackStack()
            is AuthCoordinatorAction.Authenticated -> { /* Handle authenticated action */ }
            else -> throw IllegalArgumentException("Unsupported action")
        }
    }

    @Composable
    override fun start() {
        NavHost(navController = navController, startDestination = initialScreen.title) {
            composable(AuthNavigationRoute.LOGIN.title) {
                LoginScreen(coordinator = this@AuthCoordinator)
            }
            composable(AuthNavigationRoute.SETTINGS.title) {
                SettingsScreen(coordinator = this@AuthCoordinator)
            }
        }
    }

    override fun navigate(route: NavigationRoute) {
        when (route) {
            AuthNavigationRoute.LOGIN -> {
                navController.navigate(route.title) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }

            AuthNavigationRoute.SETTINGS -> {
                navController.navigate(route.title)
            }

            else -> throw IllegalArgumentException("Unsupported route")
        }
    }
}
```

### Usage

To navigate between screens using the coordinator pattern, simply invoke the `handle` method with the appropriate action.

For example, navigating to the settings screen from the login screen:

```kotlin
@Composable
fun <T : Coordinator> LoginScreen(coordinator: T) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login Screen")
        Button(onClick = { coordinator.handle(AuthCoordinatorAction.GoToSettings) }) {
            Text("Go To Settings")
        }
        Button(onClick = { coordinator.handle(AppCoordinatorAction.GoToMain) }) {
            Text("Go To Main")
        }
    }
}
```

### Navigation Routes

Define the navigation routes within the app using `enum` classes:

```kotlin
enum class AuthNavigationRoute(override val title: String) : NavigationRoute {
    LOGIN("login"),
    SETTINGS("settings"),
}
```

### Conclusion

This example showcases a simple yet powerful implementation of the Coordinator pattern in a Kotlin Compose application. It allows for clean separation of navigation logic, making the codebase modular, testable, and easy to extend.