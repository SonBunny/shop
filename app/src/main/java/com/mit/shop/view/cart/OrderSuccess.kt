package com.mit.shop.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.mit.shop.R

@Composable
fun OrderSuccessful(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_check_circle_24),
            contentDescription = "Success Icon",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 20.dp)
        )

        Text(
            text = "Order Placed Successfully",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Your order has been successfully placed.\nYou will receive a confirmation email shortly.",
            style = TextStyle(
                fontSize = 15.sp
            ),
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Button(
            onClick = { navController.navigate("home_screen") }, // Replace "home" with the actual route you want to navigate to
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp)
                .size(height = 50.dp, width = 300.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Back to Home")
        }
    }
}
