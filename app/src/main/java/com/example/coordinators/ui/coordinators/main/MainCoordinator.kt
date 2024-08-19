package com.example.coordinators.ui.coordinators.main
import MainScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.coordinators.ui.coordinators.AppCoordinatorAction
import com.example.coordinators.ui.coordinators.Coordinator
import com.example.coordinators.ui.coordinators.CoordinatorAction
import com.example.coordinators.ui.coordinators.HostCoordinator
import com.example.coordinators.ui.coordinators.RootCoordinator
import com.example.coordinators.ui.coordinators.orders.OrdersCoordinator
import com.example.coordinators.ui.coordinators.orders.OrdersNavigationRoute
import com.example.coordinators.ui.navigation.NavHostBuilder
import com.example.coordinators.ui.navigation.Navigable

enum class MainNavigationRoute(override val route: String) : Navigable {
    MAIN("main"),
}

sealed class MainCoordinatorAction : CoordinatorAction {
    data object LogOut : MainCoordinatorAction()
    data object GoToOrders : MainCoordinatorAction()
    data object GoToMain : MainCoordinatorAction()
}


class MainCoordinator(
    override val parent: Coordinator
) : HostCoordinator {
    private var _activeCoordinator by mutableStateOf<Coordinator?>(null)
    override val activeCoordinator: Coordinator?
        get() = _activeCoordinator

    private val ordersCoordinator: OrdersCoordinator by lazy { OrdersCoordinator(this) }

    override fun setupNavigation(builder: NavHostBuilder) {
        builder.composable(MainNavigationRoute.MAIN) {
            MainScreen(coordinator = this@MainCoordinator)
        }
        ordersCoordinator.setupNavigation(builder)
    }

    @Composable
    override fun handle(action: CoordinatorAction) {
        when (action) {
            is MainCoordinatorAction.LogOut -> parent.handle(AppCoordinatorAction.LogOut)
            is MainCoordinatorAction.GoToOrders -> {
                _activeCoordinator = ordersCoordinator
                navigate(OrdersNavigationRoute.ORDER_LIST)
            }
            is MainCoordinatorAction.GoToMain -> {
                _activeCoordinator = this
                navigate(MainNavigationRoute.MAIN)
            }
            else -> throw IllegalArgumentException("Unsupported action")
        }
    }
}

class MainCoordinatorFactory {
    fun create(parent: Coordinator): Coordinator {
        return MainCoordinator(parent = parent)
    }
}
