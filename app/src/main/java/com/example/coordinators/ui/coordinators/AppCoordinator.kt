package com.example.coordinators.ui.coordinators

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.coordinators.ui.coordinators.auth.AuthCoordinator
import com.example.coordinators.ui.coordinators.auth.AuthNavigationRoute
import com.example.coordinators.ui.coordinators.main.MainCoordinator

sealed class AppCoordinatorAction : CoordinatorAction {
    data object GoToMain : AppCoordinatorAction()
    data object GoToLogin : AppCoordinatorAction()
    data object LogOut : AppCoordinatorAction()
}

class AppCoordinator(
    private val navController: NavHostController
) : RootCoordinator{

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
            is AppCoordinatorAction.GoToLogin ->  authCoordinator.start()
            is AppCoordinatorAction.LogOut ->  authCoordinator.start()
            else -> throw IllegalArgumentException("Unsupported action")
        }
    }

    @Composable
    override fun start(action: AppCoordinatorAction) {
        handle(action)
    }
}
