package com.mit.shop

import ApiService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.mit.shop.model.ProductModel
import com.mit.shop.network.cart.CartRepository
import com.mit.shop.network.product.ProductRepository

import com.mit.shop.view.*
import com.mit.shop.view.Home.*
import com.mit.shop.view.cart.CartScreen
import com.mit.shop.view.login.*
import com.mit.shop.view.product.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val apiService: ApiService by lazy { ApiService.create() }
    private val cartRepository: CartRepository by lazy { CartRepository(apiService) }
    private val sessionManager: SessionManager by lazy { SessionManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val startDestination = if (sessionManager.getUserId() != -1) {
                "home_screen"
            } else {
                "home"
            }
            val productRepository = remember { ProductRepository() }
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = startDestination) {
                composable("home") { SplashScreen(navController) }

                composable("sign_up_verify") { SignUpVerify(navController) }
                composable("forgot_pass") { ForgotPassScreen() }

                composable("home_screen") { HomeScreen(navController) }

                composable(
                    "product_list_full?category={category}",
                    arguments = listOf(navArgument("category") { nullable = true })
                ) { backStackEntry ->
                    val category = backStackEntry.arguments?.getString("category")
                    ProductListScreen(navController, category)
                }

                composable("product_detail_screen/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 0
                    ProductDetailScreen(
                        navController = navController,
                        productId = productId,
                        cartRepository = cartRepository,
                        accountId = sessionManager.getUserId()
                    )
                }





                composable("cart_screen") {
                    CartScreen(navController, cartRepository, sessionManager)
                }

                composable("order_success") { OrderSuccessful(navController) }

                composable("sign_up_screen?firstName={firstName}&lastName={lastName}&phoneNumber={phoneNumber}&email={email}&gender={gender}&birthDate={birthDate}") { backStackEntry ->
                    SignUpScreen(navController, backStackEntry)
                }

                composable("sign_up_name") { SignUpName(navController) }
                composable("sign_up_info?firstName={firstName}&lastName={lastName}") { backStackEntry ->
                    SignUpInformation(navController, backStackEntry)
                }

                composable("sign_in") { SignInScreenWrapper(navController) }
                composable("sign_up_success") { SignUpSuccessful(navController) }
            }
        }
    }
}
