package com.mit.shop.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.mit.shop.R

@Composable
fun SignUpInformation(navController: NavController){

    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize() // Ensures the Column takes up the available space
            .verticalScroll(rememberScrollState())
    ) {



            Row(  modifier = Modifier
                .padding(top = 60.dp, start = 20.dp)
            ) {


                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Sign Up - Info",
                    fontSize = 30.sp,
                    color = Color(0xFF3E4958),
                    modifier = Modifier
                        .padding(top = 7.dp, start = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Phone Number",
                    fontSize = 15.sp,
                    color = Color(0xFF3E4958),
                    modifier = Modifier
                        .padding(top = 7.dp, start = 35.dp)
                )
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    placeholder = { Text("0210 083 69014") },
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
                    text = "Email",
                    fontSize = 15.sp,
                    color = Color(0xFF3E4958),
                    modifier = Modifier
                        .padding(top = 7.dp, start = 35.dp)
                )

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("example@example.com") },
                    modifier = Modifier
                        .padding(start = 30.dp, top = 10.dp, end = 35.dp)
                        .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFF7F8F9),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                )

                Spacer(modifier = Modifier.height(20.dp))


                Text(
                    text = "Gender",
                    fontSize = 15.sp,
                    color = Color(0xFF3E4958),
                    modifier = Modifier
                        .padding(top = 7.dp, start = 35.dp)
                )
                TextField(
                    value = gender,
                    onValueChange = { gender = it },
                    placeholder = { Text("Select Gender") },
                    modifier = Modifier
                        .padding(start = 30.dp, top = 10.dp, end = 35.dp)
                        .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFF7F8F9),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Birthdate",
                    fontSize = 15.sp,
                    color = Color(0xFF3E4958),
                    modifier = Modifier
                        .padding(top = 7.dp, start = 35.dp)
                )

                TextField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    placeholder = { Text("2002-01-05") },
                    modifier = Modifier
                        .padding(start = 30.dp, top = 10.dp, end = 35.dp)
                        .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFF7F8F9),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                )


        // Button
        Button(
            onClick = { navController.navigate("sign_up_verify") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Center button horizontally
                .padding(vertical = 120.dp)
                .size(height = 50.dp, width = 300.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Next")
        }

    }
}