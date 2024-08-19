package com.example.coordinators.di

import com.example.coordinators.ui.coordinators.AppCoordinator
import com.example.coordinators.ui.coordinators.RootCoordinator
import com.example.coordinators.ui.coordinators.auth.AuthCoordinatorFactory
import com.example.coordinators.ui.coordinators.main.MainCoordinatorFactory
import com.example.coordinators.ui.coordinators.orders.OrdersCoordinatorFactory
import org.koin.dsl.module

val AppDIModule = module {
    single { AuthCoordinatorFactory() }
    single {
        MainCoordinatorFactory(
            ordersCoordinatorFactory = OrdersCoordinatorFactory())
    }
    single<RootCoordinator> {
        AppCoordinator(
            authCoordinatorFactory = get(),
            mainCoordinatorFactory = get()
        )
    }
}