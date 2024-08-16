package com.example.coordinators.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.coordinators.ui.coordinators.Coordinator
import com.example.coordinators.ui.coordinators.auth.AuthCoordinatorAction

@Composable
fun <T : Coordinator> SettingsScreen(coordinator: T) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Settings Screen")
        Button(onClick = { coordinator.handle(AuthCoordinatorAction.GoBack) }) {
            Text("Go Back")
        }
    }
}
