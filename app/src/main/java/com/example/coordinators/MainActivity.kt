package com.example.coordinators

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.remember
import com.example.coordinators.ui.coordinators.AppCoordinator
import com.example.coordinators.ui.coordinators.AppCoordinatorAction
import com.example.coordinators.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val appCoordinator = remember {
                    AppCoordinator( navController)
                }

                AppNavHost(appCoordinator)
            }
        }
    }
}

@Composable
fun AppNavHost(appCoordinator: AppCoordinator) {
    appCoordinator.start(action = AppCoordinatorAction.GoToLogin)
}

