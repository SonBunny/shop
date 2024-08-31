package com.mit.shop.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.mit.shop.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignUpName(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }


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
                text = "Sign Up - Name",
                fontSize = 30.sp,
                color = Color(0xFF3E4958),
                modifier = Modifier
                    .padding(top = 7.dp, start = 20.dp)
            )
        }

        // Add padding or spacing between the title and the text fields
        Spacer(modifier = Modifier.height(16.dp))

        // TextField for User Input
        Text(
            text = "First Name",
            fontSize = 15.sp,
            color = Color(0xFF3E4958),
            modifier = Modifier
                .padding(top = 7.dp, start = 35.dp)
        )
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            placeholder = { Text("John") },
            modifier = Modifier
                .padding(start = 30.dp, top = 10.dp, end = 35.dp)
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
            text = "Last Name",
            fontSize = 15.sp,
            color = Color(0xFF3E4958),
            modifier = Modifier
                .padding(top = 7.dp, start = 35.dp)
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            placeholder = { Text("Doe") },
            modifier = Modifier
                .padding(start = 30.dp, top = 10.dp, end = 35.dp)
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


            // Button
            Button(
                onClick = { navController.navigate("sign_up_info") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Center button horizontally
                    .padding(vertical = 320.dp)
                    .size(height = 50.dp, width = 300.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Next")
            }

        Text(
            text = "Already have an account? Sign In",
            color = Color.Black,
            fontSize = 13.sp,
            modifier = Modifier
                .offset(y = (-235).dp)
                .padding(start = 100.dp)
                .clickable{navController.navigate("sign_up_name")},
        )

    }
}
