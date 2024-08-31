package com.mit.shop.view


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.mit.shop.R


@Composable
fun SignUpScreen(navController: NavController) {


    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize() // Ensures the Column takes up the available space
            .verticalScroll(rememberScrollState())
    ) {
        // Row with Back Button and Title
        Row(
            modifier = Modifier
                .padding(top = 60.dp, start = 20.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Sign Up",
                fontSize = 30.sp,
                color = Color(0xFF3E4958),
                modifier = Modifier
                    .padding(top = 7.dp, start = 20.dp)
            )
        }



        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Password",
            fontSize = 15.sp,
            color = Color(0xFF3E4958),
            modifier = Modifier
                .padding(top = 7.dp, start = 35.dp)
        )

        TextField(
            value = password, // State variable to hold the text field value
            onValueChange = { password = it },
            placeholder = { Text("Password1!") }, // Optional label
            visualTransformation = PasswordVisualTransformation(), // Mask password input
            modifier = Modifier
                .padding(start= 30.dp,top = 10.dp, end = 35.dp)
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
            text = "Confirm Password",
            fontSize = 15.sp,
            color = Color(0xFF3E4958),
            modifier = Modifier
                .padding(top = 7.dp, start = 35.dp)
        )

        TextField(
            value = confirmpassword, // State variable to hold the text field value
            onValueChange = { confirmpassword = it },
            placeholder = { Text("confirm your password") }, // Optional label
            visualTransformation = PasswordVisualTransformation(), // Mask password input
            modifier = Modifier
                .padding(start= 30.dp,top = 10.dp, end = 35.dp)
                .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp), // Custom shape
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF7F8F9), // Background color of the text field
                focusedIndicatorColor = Color.Transparent, // Hide the focused indicator
                unfocusedIndicatorColor = Color.Transparent // Hide the unfocused indicator
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button
        Button(
            onClick = { navController.navigate("sign_up_success") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Center button horizontally
                .padding(vertical = 120.dp)
                .size(height = 50.dp, width = 300.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Sign Up")
        }
    }
}