import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.coordinators.ui.coordinators.Coordinator
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.coordinators.ui.coordinators.CoordinatorAction
import com.example.coordinators.ui.coordinators.main.MainCoordinatorAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun <T : Coordinator> MainScreen(coordinator: T) {
    var action by remember { mutableStateOf<CoordinatorAction?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Logout button at the top-left corner
        IconButton(
            onClick = { action = MainCoordinatorAction.LogOut },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Logout"
            )
        }

        // Title at the top center
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 24.dp)
        )

        // Spinning cog in the center
        val rotation = rememberCoroutineScope().animateRotation()
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Spinning Cog",
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center)
                .rotate(rotation)
        )

        // Go to Orders button at the bottom center
        Button(
            onClick = { action = MainCoordinatorAction.GoToOrders  },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Go to Orders")
        }
    }

    action?.let {
        coordinator.handle(it)
        action = null
    }
}


@Composable
fun CoroutineScope.animateRotation(): Float {
    var rotation by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            rotation += 10f
            delay(100L)  // Adjust speed by changing the delay
        }
    }
    return rotation
}
