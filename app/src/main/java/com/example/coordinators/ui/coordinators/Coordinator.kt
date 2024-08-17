package com.example.coordinators.ui.coordinators

import androidx.compose.runtime.Composable
import com.example.coordinators.ui.navigation.NavHostBuilder
import com.example.coordinators.ui.navigation.Navigable
import com.example.coordinators.ui.navigation.Navigator

interface CoordinatorAction

sealed class GeneralAction : CoordinatorAction {
    data class Done(val data: Any) : GeneralAction()
    data class Cancel(val reason: Any) : GeneralAction()
}

interface Router {
    val activeCoordinator: Coordinator?
    val navigator: Navigator
}

interface Coordinator {
    fun navigate(route: Navigable)
    fun setupNavigation(builder: NavHostBuilder)

    @Composable
    fun handle(action: CoordinatorAction)
}

interface NavigatorCoordinator: Coordinator,Router

interface RootCoordinator: NavigatorCoordinator{
    @Composable
    fun start(action: AppCoordinatorAction)
}