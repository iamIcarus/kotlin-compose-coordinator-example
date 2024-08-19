package com.example.coordinators.ui.view.screens.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coordinators.ui.coordinators.Coordinator
import com.example.coordinators.ui.coordinators.CoordinatorAction
import com.example.coordinators.ui.coordinators.orders.OrdersCoordinatorAction

@Composable
fun <T : Coordinator> OrderListScreen(coordinator: T) {
    var action by remember { mutableStateOf<CoordinatorAction?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Back to Main Button
        Button(
            onClick = { action = OrdersCoordinatorAction.GoBackToMain },
            modifier = Modifier.align(Alignment.Start)) {
            Text("Back to Main")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text("Select a Donut", style = MaterialTheme.typography.h5, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(16.dp))

        // List of Donuts
        val donuts = listOf("Glazed", "Chocolate", "Strawberry", "Cinnamon", "Blueberry")
        donuts.forEach { donut ->
            Button(
                onClick = { action = OrdersCoordinatorAction.ViewOrderDetails(donut) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(donut)
            }
        }
    }

    action?.let {
        coordinator.handle(it)
        action = null
    }
}
