package com.example.coordinators.ui.coordinators

interface NavigationRoute {
    val title: String
}

interface Router {
    fun navigate(route: NavigationRoute)
}