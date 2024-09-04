package com.mit.shop.view.Home


import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext
import com.mit.shop.SessionManager

import com.mit.shop.view.nav.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.compose.ui.Alignment


import com.mit.shop.R
import com.mit.shop.model.ProductModel
import com.mit.shop.network.product.ProductRepository
import com.mit.shop.ui.theme.*
import com.mit.shop.view.nav.LogoutConfirmationDialog
import com.mit.shop.view.product.*


@Composable
fun HomeScreen(navController: NavController) {

    var products by remember { mutableStateOf<List<ProductModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            products = ProductRepository().fetchProducts() ?: emptyList()
            Log.d("HomeScreen", "Products fetched successfully")
        } catch (e: Exception) {
            error = "Failed to load products. Please try again."
            Log.e("HomeScreen", "Error fetching products", e)
        } finally {
            isLoading = false
            Log.d("HomeScreen", "Loading finished")
        }
    }

    Scaffold(
        bottomBar = {
            Column {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else if (error != null) {
                    Text(text = error!!, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    NewProducts(navController = navController, products = products)
                    BottomNavigationBar(navController = navController)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (error != null) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (products.isEmpty()) {
                Text(
                    text = "No products available.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                MainScreen(navController, products)
            }
        }
    }
}



@Composable
fun NewProducts(navController: NavController, products: List<ProductModel>) {

    val newProducts = products.filter { it.isNew }

    Box(
        modifier = Modifier
            .height(370.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 50.dp,
                    topEnd = 50.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .fillMaxWidth()
            .background(Color(0xFFF7F8F9))
            .zIndex(0f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start=30.dp,top=20.dp,end=30.dp)
            ) {
                Text(
                    text = "New Products",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { navController.navigate("product_list_full") },
                    modifier = Modifier
                        .size(height = 50.dp, width = 100.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF1152FD),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("View all")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start=10.dp,end=10.dp)
            ){
                CarouselProduct(products = newProducts, navController = navController)
            }
        }
    }
}




@Composable
fun MainScreen(navController: NavController, products: List<ProductModel>) {
    var search by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .height(220.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 50.dp,
                    bottomStart = 50.dp
                )
            )
            .background(BackgroundColor)
            .fillMaxWidth()
    )

    Row {
        TextField(
            value = search,
            onValueChange = { search = it },
            placeholder = { Text("Search") },
            modifier = Modifier
                .padding(start = 40.dp, top = 50.dp, end = 40.dp)
                .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF7F8F9),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ProductShowcase(navController, products = products)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 350.dp, start = 45.dp, end = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ClickableIconBox(iconId = R.drawable.men_blue, category = "Male") {
            navController.navigate("product_list_full?category=Male")
        }
        ClickableIconBox(iconId = R.drawable.women_blue, category = "Female") {
            navController.navigate("product_list_full?category=Female")
        }
        ClickableIconBox(iconId = R.drawable.baby_blue, category = "Baby") {
            navController.navigate("product_list_full?category=Baby")
        }
        ClickableIconBox(iconId = R.drawable.sales_blue, category = "Sales") {
            navController.navigate("product_list_full?category=Sales")
        }
    }
}






@Composable
fun ClickableIconBox(iconId: Int, category: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF1151FB).copy(alpha = 0.17f))
            .width(55.dp)
            .height(55.dp)
            .clickable {
                onClick()
            }
    ) {
        val icon: Painter = painterResource(id = iconId)
        Image(
            painter = icon,
            contentDescription = "Icon",
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = {
                navController.navigate("home_screen")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") },
            selected = false,
            onClick = {
                navController.navigate("cart_screen")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Account") },
            label = { Text("Account") },
            selected = false,
            onClick = {
                showLogoutDialog = true
            }
        )
    }

    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                showLogoutDialog = false
                sessionManager.clearSession()
                navController.navigate("sign_in") {
                    popUpTo("sign_in") { inclusive = true }
                }
            },
            onDismiss = {
                showLogoutDialog = false
            }
        )
    }

}
