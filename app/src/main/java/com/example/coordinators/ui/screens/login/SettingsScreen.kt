package com.example.coordinators.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coordinators.ui.coordinators.Coordinator
import com.example.coordinators.ui.coordinators.auth.AuthCoordinatorAction

@Composable
fun <T : Coordinator> SettingsScreen(coordinator: T) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
        ) {
            item {
                var username by remember { mutableStateOf("John Doe") }
                Text(text = "Username", style = MaterialTheme.typography.subtitle1)
                BasicTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }

            item {
                // Example of a switch setting
                var notificationsEnabled by remember { mutableStateOf(true) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Enable Notifications",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { coordinator.handle(AuthCoordinatorAction.GoBack) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { coordinator.handle(AuthCoordinatorAction.GoBack) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Go Back")
            }
        }
    }
}
