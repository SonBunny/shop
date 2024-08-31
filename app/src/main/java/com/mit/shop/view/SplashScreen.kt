package com.mit.shop.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mit.shop.ui.theme.*
import com.mit.shop.R
import androidx.navigation.compose.*



@Composable
fun SplashScreen(navController: NavController){


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundColor // Set the background color here
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sneakit), // Replace 'logo' with your image resource name
                contentDescription = "App Logo",
                contentScale = ContentScale.Fit, // Adjust as needed
                modifier = Modifier.size(128.dp) // Adjust size as needed
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("sign_up_name") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Background color
                    contentColor = Color(0xFF1152FD) // Text color
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .size(width = 500.dp, height = 60.dp)
                    .offset(x = 0.dp, y = 200.dp)
            ) {
                Text(text = "Sign Up", color = Color(0xFF1152FD))
            }
            Button(
                onClick = { navController.navigate("sign_in_view") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1152FD), // Background color
                    contentColor = Color(0xFF1152FD) // Text color
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .size(width = 500.dp, height = 60.dp)
                    .offset(x = 0.dp, y = 200.dp)
            ) {
                Text(text = "Sign In", color = Color.White)
            }
        }
    }
}