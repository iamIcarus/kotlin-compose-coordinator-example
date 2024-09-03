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
import com.example.coordinators.ui.coordinators.auth.AuthCoordinatorFactory
import com.example.coordinators.ui.coordinators.orders.OrdersCoordinator
import com.example.coordinators.ui.coordinators.orders.OrdersCoordinatorFactory
import com.example.coordinators.ui.coordinators.orders.OrdersNavigationRoute
import com.example.coordinators.ui.navigation.NavHost
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
    override val parent: Coordinator,
    private val ordersCoordinatorFactory: OrdersCoordinatorFactory
    ) : HostCoordinator {
    private var _activeCoordinator by mutableStateOf<Coordinator?>(null)
    override val activeCoordinator: Coordinator?
        get() = _activeCoordinator

    private val ordersCoordinator: Coordinator by lazy { ordersCoordinatorFactory.create(parent = this) }

    override var rootBuilder: NavHostBuilder? = null

    override fun setupNavigation(builder: NavHostBuilder) {
        builder.composable(MainNavigationRoute.MAIN) {
            MainScreen(coordinator = this@MainCoordinator)
        }

        rootBuilder = builder
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

        if(rootBuilder != null)
            _activeCoordinator?.setupNavigation(rootBuilder!!)
    }
}

class MainCoordinatorFactory(
    private val ordersCoordinatorFactory: OrdersCoordinatorFactory
) {
    fun create(parent: Coordinator): Coordinator {
        return MainCoordinator(
            parent = parent,
            ordersCoordinatorFactory = ordersCoordinatorFactory
        )
    }
}
