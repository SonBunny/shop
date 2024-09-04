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
fun SignUpSuccessful(navController: NavController){



    Column(
        modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,


    ) {

        Row(
            modifier = Modifier
                .padding(top = 100.dp, start = 90.dp)
        ){

            Image(
                painter = painterResource(id = R.drawable.staricon),
                contentDescription = "App Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(80.dp)



            )
        }


            Row(


            ){
                Image(
                    painter = painterResource(id = R.drawable.baseline_check_circle_24), // Replace 'logo' with your image resource name
                    contentDescription = "App Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(250.dp)


                )
            }

        Text(
            text = "Account creation",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black,
            modifier = Modifier

        )

        Text(
            text = "successful",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black,
            modifier = Modifier

        )
        Text(
            text = "        You have successfully created your\naccount. Please use your email and password\nwhen logging in",
            style = TextStyle(
                fontSize = 15.sp,

            ),
            color = Color.Black,
            modifier = Modifier
                .padding(top=20.dp),
            textAlign = TextAlign.Center

        )

        Button(
            onClick = { navController.navigate("sign_in_view") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 65.dp)
                .size(height = 50.dp, width = 300.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Back to Login")
        }
    }
}