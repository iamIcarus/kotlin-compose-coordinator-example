package com.example.coordinators.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Navigator(initialRoute: String) {

    private data class Route(val name: String, val param: String?)

    private var _route by mutableStateOf(Route(initialRoute, null))

    val route get() = _route.name
    val param get() = _route.param

    fun navigateTo(route: String, param: String? = null) {
        _route = Route(route, param)
    }

    // Reset method to clear the navigation stack
    fun resetTo(route: String) {
        _route = Route(route, null)
    }
}