package com.example.coordinators.ui.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.coordinators.ui.coordinators.AppCoordinatorAction
import com.example.coordinators.ui.coordinators.RootCoordinator
import com.example.coordinators.ui.theme.MyApplicationTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val appCoordinator: RootCoordinator by inject()
                appCoordinator.start(AppCoordinatorAction.StartLoginFlow)
            }
        }
    }
}