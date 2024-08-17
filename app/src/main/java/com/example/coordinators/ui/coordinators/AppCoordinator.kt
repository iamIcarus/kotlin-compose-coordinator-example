package com.example.coordinators.ui.coordinators

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.coordinators.ui.coordinators.auth.AuthCoordinator
import com.example.coordinators.ui.coordinators.auth.AuthNavigationRoute
import com.example.coordinators.ui.coordinators.main.MainCoordinator
import com.example.coordinators.ui.coordinators.main.MainNavigationRoute
import com.example.coordinators.ui.navigation.NavHost
import com.example.coordinators.ui.navigation.NavHostBuilder
import com.example.coordinators.ui.navigation.Navigable
import com.example.coordinators.ui.navigation.Navigator


sealed class AppCoordinatorAction : CoordinatorAction {
    data object StartMainFlow : AppCoordinatorAction()
    data object StartLoginFlow : AppCoordinatorAction()
    data object LogOut : AppCoordinatorAction()
}

class AppCoordinator() : RootCoordinator {
    override val parent: Coordinator? = null

    private var _activeCoordinator by mutableStateOf<Coordinator?>(null)
    override val activeCoordinator: Coordinator?
        get() = _activeCoordinator

    // Root navigator that manages all flows
    override val navigator: Navigator = Navigator("root")

    private val authCoordinator: Coordinator by lazy { AuthCoordinator(this) }
    private val mainCoordinator: Coordinator by lazy { MainCoordinator(this) }


    @Composable
    override fun handle(action: CoordinatorAction) {
        when (action) {
            is AppCoordinatorAction.StartMainFlow -> {
                _activeCoordinator = mainCoordinator
                navigator.navigateTo(MainNavigationRoute.MAIN.route)
            }
            is AppCoordinatorAction.StartLoginFlow -> {
                _activeCoordinator = authCoordinator
                navigator.navigateTo(AuthNavigationRoute.LOGIN.route)
            }
            is AppCoordinatorAction.LogOut -> {
                _activeCoordinator = authCoordinator
                navigator.navigateTo(AuthNavigationRoute.LOGIN.route)
            }
            else -> throw IllegalArgumentException("Unsupported action")
        }

        NavHost(navigator = navigator) {
            _activeCoordinator?.setupNavigation(this)
        }
    }

    @Composable
    override fun start(action: AppCoordinatorAction) {
        handle(action)
    }

    override fun navigate(route: Navigable) {
        navigator.navigateTo(route.route)
    }
}

