package com.example.coordinators.ui.coordinators.orders
import androidx.compose.runtime.Composable
import com.example.coordinators.ui.coordinators.Coordinator
import com.example.coordinators.ui.coordinators.CoordinatorAction
import com.example.coordinators.ui.coordinators.HostCoordinator
import com.example.coordinators.ui.coordinators.main.MainCoordinatorAction
import com.example.coordinators.ui.navigation.NavHostBuilder
import com.example.coordinators.ui.navigation.Navigable
import com.example.coordinators.ui.screens.orders.OrderDetailScreen
import com.example.coordinators.ui.screens.orders.OrderListScreen

enum class OrdersNavigationRoute(override val route: String) : Navigable {
    ORDER_LIST("orderList"),
    ORDER_DETAIL("orderDetail"),
}

sealed class OrdersCoordinatorAction : CoordinatorAction {
    data class ViewOrderDetails(val orderId: String) : OrdersCoordinatorAction()
    data object GoBackToMain : OrdersCoordinatorAction()
    data object GoToOrders : OrdersCoordinatorAction()
}

class OrdersCoordinator(private val parent: HostCoordinator) : Coordinator {
    //private val localNavigator: Navigator = Navigator(OrdersNavigationRoute.ORDER_LIST.route)

    override fun setupNavigation(builder: NavHostBuilder) {
        builder.composable(OrdersNavigationRoute.ORDER_LIST) {
            OrderListScreen(coordinator = this@OrdersCoordinator)
        }

        builder.composable(OrdersNavigationRoute.ORDER_DETAIL) {
            OrderDetailScreen(coordinator = this@OrdersCoordinator)
        }
    }

    @Composable
    override fun handle(action: CoordinatorAction) {
        when (action) {
            is OrdersCoordinatorAction.ViewOrderDetails -> {
                navigate(OrdersNavigationRoute.ORDER_DETAIL)
            }
            is OrdersCoordinatorAction.GoToOrders -> {
                navigate(OrdersNavigationRoute.ORDER_LIST)
            }
            is OrdersCoordinatorAction.GoBackToMain -> {
                parent.handle(MainCoordinatorAction.GoToMain)
            }
            else -> throw IllegalArgumentException("Unsupported action")
        }
    }

    override fun navigate(route: Navigable) {
        parent.navigate(route)
    }
}
