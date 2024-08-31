package com.mit.shop.view.Home

import android.view.RoundedCorner
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.Role.Companion.Button

import androidx.navigation.compose.rememberNavController

import com.mit.shop.R
import com.mit.shop.ui.theme.*
import com.mit.shop.view.product.*
@Composable
fun HomeScreen(navController: NavController) {
    val products = listOf(
        Product(1, "Air Max 2090", "Description of Product 1", R.drawable.product1,false),
        Product(2, "Nike React Miler", "Description of Product 2", R.drawable.product2,true),
        Product(3, "Air Max 270", "Description of Product 3", R.drawable.product3,true)
    )

    Scaffold(
        bottomBar = {

            Column {
                NewProducts(navController = navController,products)
                BottomNavigationBar(navController = navController)
            }




        }
    ) { innerPadding ->
        // Ensure that the content respects the bottom navigation bar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply padding to avoid overlapping with the BottomNavigation
        ) {

            MainScreen(navController,products)

        }
    }
}

@Composable
fun NewProducts(navController: NavController, products: List<Product>){
    Box(
        modifier = Modifier
            .height(370.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 50.dp, // Round the top-left corner
                    topEnd = 50.dp,    // Keep the top-right corner square
                    bottomEnd = 0.dp, // Round the bottom-right corner
                    bottomStart = 0.dp // Keep the bottom-left corner square
                )
            )
            .fillMaxWidth()
            .background(Color(0xFFF7F8F9)) // Set background color or image here

            .zIndex(0f)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {
                Text(
                    text = "New Products",
                    fontSize = 20.sp,
                    modifier = Modifier
                     .padding(top = 10.dp)
                )

                Spacer(modifier = Modifier.weight(1f)) // This will push the button to the end of the row

                Button(
                    onClick = { navController.navigate("all_product") },
                    modifier = Modifier
                        .size(height = 50.dp, width = 100.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF1152FD), // Background color
                        contentColor = Color.White // Text color
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("View all")
                }
            }

            CarouselProduct(navController, products)
        }




    }

}


@Composable
fun MainScreen(navController: NavController, products: List<Product>){
    var search by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .height(220.dp) // Adjust the thickness of the line
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp, // Round the top-left corner
                    topEnd = 0.dp,    // Keep the top-right corner square
                    bottomEnd = 50.dp, // Round the bottom-right corner
                    bottomStart = 50.dp // Keep the bottom-left corner square
                )
            )
            .background(BackgroundColor)
            .fillMaxWidth() // Makes the line fill the width of the parent

    )

    Row(


    ){
        TextField(
            value = search,
            onValueChange = { search = it },
            placeholder = { Text("Search") },
            modifier = Modifier
                .padding(start = 40.dp, top = 50.dp, end = 40.dp)
                .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp), // Custom shape
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF7F8F9), // Background color of the text field
                focusedIndicatorColor = Color.Transparent, // Hide the focused indicator
                unfocusedIndicatorColor = Color.Transparent // Hide the unfocused indicator
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
    ){
        ClickableIconBox(iconId = R.drawable.men_blue) {
            navController.navigate("men_screen")
        }
        ClickableIconBox(iconId = R.drawable.women_blue) {
            navController.navigate("women_screen")
        }
        ClickableIconBox(iconId = R.drawable.baby_blue) {
            navController.navigate("baby_screen")
        }
        ClickableIconBox(iconId = R.drawable.sales_blue) {
            navController.navigate("sales_screen")
        }

    }
}




@Composable
fun SearchScreen(navController: NavController) {
    // Your SearchScreen content
}

@Composable
fun FavoritesScreen(navController: NavController) {
    // Your FavoritesScreen content
}

@Composable
fun ProfileScreen(navController: NavController) {
    // Your ProfileScreen content
}



@Composable
fun ClickableIconBox(iconId: Int, onClick: () -> Unit) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF1151FB).copy(alpha = 0.17f))
            .width(55.dp)
            .height(55.dp)
            .clickable { onClick() }
    ){
        val icon: Painter = painterResource(id = iconId) // Load PNG resource
        Image(
            painter = icon,
            contentDescription = "Icon",
            modifier = Modifier
                .size(40.dp) // Make the image fill the box
                .clip(RoundedCornerShape(10.dp)) // Match the box's shape
        )

    }

}


@Composable
fun BottomNavigationBar(navController: NavController) {
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
                navController.navigate("account_screen")
            }
        )
    }

}
