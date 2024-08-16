package com.example.coordinators.ui.coordinators.auth

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coordinators.ui.coordinators.Coordinator
import com.example.coordinators.ui.coordinators.CoordinatorAction
import com.example.coordinators.ui.coordinators.GeneralAction
import com.example.coordinators.ui.coordinators.NavigationRoute
import com.example.coordinators.ui.coordinators.Router
import com.example.coordinators.ui.screens.login.SettingsScreen

enum class AuthNavigationRoute(override val title: String) : NavigationRoute {
    LOGIN("login"),
    SETTINGS("settings"),
}

sealed class AuthCoordinatorAction : CoordinatorAction {
    data object GoToSettings : AuthCoordinatorAction()
    data object Authenticated : AuthCoordinatorAction()
    data object GoBack : AuthCoordinatorAction()
}

class AuthCoordinator(
    private val navController: NavHostController,
    override val initialScreen: NavigationRoute = AuthNavigationRoute.LOGIN
): Coordinator , Router {

    override fun handle(action: CoordinatorAction) {
        when (action) {
            is GeneralAction.Done -> println("Done action with data: ${action.data}")
            is GeneralAction.Cancel -> println("Cancelled with reason: ${action.reason}")
            is AuthCoordinatorAction.GoToSettings -> navigate(AuthNavigationRoute.SETTINGS)
            is AuthCoordinatorAction.GoBack -> navController.popBackStack()
            is AuthCoordinatorAction.Authenticated -> { /* not implemented*/ }
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