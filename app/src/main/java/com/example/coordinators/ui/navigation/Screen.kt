package com.example.coordinators.ui.navigation

sealed class Screen(override val route: String): Navigable {
    data object Home : Screen("home") {
        const val href = "#"
    }
    data object Details: Screen("details")
    data object Faq: Screen("faq") {
        const val href = "#faq"
    }

    fun href(param: String) = "#$route/$param"

}