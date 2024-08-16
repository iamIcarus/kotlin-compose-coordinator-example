package com.example.coordinators.ui.coordinators.orders
import androidx.navigation.NavController

class OrdersCoordinator(private val navController: NavController) {
    fun start() {
        navController.navigate("orderListScreen")
    }

    fun goToOrderDetail(orderId: String) {
        navController.navigate("orderDetailScreen/$orderId")
    }
}