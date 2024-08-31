package com.mit.shop

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.navigation.compose.*
import com.mit.shop.view.*
import com.mit.shop.view.Home.HomeScreen
import com.mit.shop.view.login.*



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home_screen") {
                composable("home") { SplashScreen(navController) }
                composable("sign_up_name") { SignUpName(navController) }
                composable("sign_up_info") { SignUpInformation(navController) }
                composable("sign_up_verify") { SignUpVerify(navController) }
                composable("sign_up_success") { SignUpSuccessful(navController) }

                composable("sign_up_pass") { SignUpScreen(navController) }

                //Sign In
                composable("sign_in_view") { SignInScreen(navController) }
                composable("forgot_pass") { ForgotPassScreen() }


                //homescreen
                composable("home_screen") { HomeScreen(navController) }


            }
        }
    }
}