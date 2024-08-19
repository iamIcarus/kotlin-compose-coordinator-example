package com.example.coordinators.di

import com.example.coordinators.ui.coordinators.AppCoordinator
import com.example.coordinators.ui.coordinators.RootCoordinator
import com.example.coordinators.ui.coordinators.auth.AuthCoordinatorFactory
import com.example.coordinators.ui.coordinators.main.MainCoordinatorFactory
import org.koin.dsl.module

val AppDIModule = module {
    single { AuthCoordinatorFactory() }
    single { MainCoordinatorFactory() }
    single<RootCoordinator> {
        AppCoordinator(
            authCoordinatorFactory = get(),
            mainCoordinatorFactory = get()
        )
    }
}