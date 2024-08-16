package com.example.coordinators.ui.coordinators.main

import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.coordinators.ui.screens.MainScreen
import com.example.coordinators.ui.screens.orders.OrderDetailScreen
import com.example.coordinators.ui.screens.orders.OrderListScreen

class MainCoordinator(private val navController: NavHostController) {
    @Composable
    fun start() {
        NavHost(navController = navController, startDestination = "mainScreen") {
            composable("mainScreen") {
                MainScreen(onOrdersClick = {
                    goToOrders()
                })
            }
            composable("orderListScreen") {
                OrderListScreen(onOrderClick = { orderId ->
                    goToOrderDetail(orderId)
                })
            }
            composable("orderDetailScreen/{orderId}") { backStackEntry ->
                val orderId = backStackEntry.arguments?.getString("orderId")
                OrderDetailScreen(orderId, onBackClick = {
                    navController.popBackStack()  // Navigates back to the order list
                })
            }
        }
    }

    private fun goToOrders() {
        navController.navigate("orderListScreen")
    }

    private fun goToOrderDetail(orderId: String) {
        navController.navigate("orderDetailScreen/$orderId")
    }
}