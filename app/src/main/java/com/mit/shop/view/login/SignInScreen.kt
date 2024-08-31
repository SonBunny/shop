package com.mit.shop.view.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import com.mit.shop.R

@Composable
fun SignInScreen(navController: NavController){
    var firstName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
        .padding(16.dp)
        .fillMaxSize() // Ensures the Column takes up the available space
        .verticalScroll(rememberScrollState())
    ){
        Row(
            modifier = Modifier
                .padding(top = 80.dp, start = 20.dp)
        ) {
            Text(
                text = "Sign In",
                fontSize = 30.sp,
                color = Color(0xFF3E4958),
                modifier = Modifier
                    .padding(top = 30.dp, start = 20.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Add padding or spacing between the title and the text fields
        Spacer(modifier = Modifier.height(45.dp))

        // TextField for User Input
        Text(
            text = "Email",
            fontSize = 15.sp,
            color = Color(0xFF3E4958),
            modifier = Modifier
                .padding(top = 7.dp, start = 40.dp)
        )
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            placeholder = { Text("juandelacruz@gmail.com") },
            modifier = Modifier
                .padding(start = 40.dp, top = 10.dp, end = 35.dp)
                .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp), // Custom shape
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF7F8F9), // Background color of the text field
                focusedIndicatorColor = Color.Transparent, // Hide the focused indicator
                unfocusedIndicatorColor = Color.Transparent // Hide the unfocused indicator
            ),
        )

        Spacer(modifier = Modifier.height(20.dp))


        Text(
            text = "Password",
            fontSize = 15.sp,
            color = Color(0xFF3E4958),
            modifier = Modifier
                .padding(top = 7.dp, start = 40.dp)
        )

        TextField(
            value = password, // State variable to hold the text field value
            onValueChange = { password = it },
            placeholder = { Text("Password1!") }, // Optional label
            visualTransformation = PasswordVisualTransformation(), // Mask password input
            modifier = Modifier
                .padding(start= 40.dp,top = 10.dp, end = 35.dp)
                .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp), // Custom shape
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF7F8F9), // Background color of the text field
                focusedIndicatorColor = Color.Transparent, // Hide the focused indicator
                unfocusedIndicatorColor = Color.Transparent // Hide the unfocused indicator
            ),
        )

        Text(
            text = "Forgot password?",
            fontSize = 15.sp,
            color = Color(0xFF3E4958),
                modifier = Modifier
                    .padding(top = 15.dp, start = 250.dp)
                    .clickable{navController.navigate("forgot_pass")},
        )

        Button(
            onClick = { navController.navigate("home_screen") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Center button horizontally
                .padding(top=40.dp)
                .size(height = 50.dp, width = 320.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Sign in")
        }


        Row(
            modifier = Modifier
                .padding(start = 70.dp, top=20.dp),


        ){

            Box(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .offset( y = (7).dp)
                    .height(2.dp) // Adjust the thickness of the line
                    .width(80.dp) // Makes the line fill the width of the parent
                    .background(Color(0xFFD5DDE0)) // Change the color to your preference
            )
            Text(
                text = "Or Sign In with",
                color = Color.Black,
                fontSize = 13.sp
            )
            Box(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .offset( y = (7).dp)
                    .height(2.dp) // Adjust the thickness of the line
                    .width(80.dp) // Makes the line fill the width of the parent
                    .background(Color(0xFFD5DDE0)) // Change the color to your preference
            )



        }


        Text(
            text = "Don't have an account? Sign Up",
            color = Color.Black,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(top = 250.dp, start = 100.dp)
            .clickable{navController.navigate("sign_up_name")},
        )



    }
}