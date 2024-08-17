package com.example.coordinators.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@Composable
fun NavHost(navigator: Navigator, navigations: @Composable NavHostBuilder.() -> Unit) {
    // Remember the builder to avoid recomposing it unnecessarily
    val builder = remember { NavHostBuilder() }
    navigations(builder)

    // Retrieve the current route configuration
    val routeConfig = builder[navigator.route]

    // Render the appropriate composable based on the current route
    routeConfig?.let { config ->
        // Trigger the onActivated callback
        LaunchedEffect(navigator.route) {
            config.onActivated(navigator.param)
        }
        // Render the composable within the Compose context
        config.composable()
    }
}

class NavHostBuilder {

    data class RouteConfiguration(
        val onActivated: (String?) -> Unit = {},
        val composable: @Composable () -> Unit
    )

    private val routes = mutableMapOf<String, RouteConfiguration>()

    fun composable(
        screen: Navigable,
        onActivated: (String?) -> Unit = {},
        composable: @Composable () -> Unit
    ) {
        routes[screen.route] = RouteConfiguration(onActivated, composable)
    }

    operator fun get(route: String) = routes[route]
}
