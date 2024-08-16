package com.example.coordinators.ui.coordinators

import androidx.compose.runtime.Composable

interface CoordinatorAction

sealed class GeneralAction : CoordinatorAction {
    data class Done(val data: Any) : GeneralAction()
    data class Cancel(val reason: Any) : GeneralAction()
}

interface Coordinator {
    @Composable
    fun start()

    fun handle(action: CoordinatorAction)

    val initialScreen: NavigationRoute
}

interface RootCoordinator {
    @Composable
    fun start(action: AppCoordinatorAction)

    @Composable
    fun handle(action: CoordinatorAction)
}