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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mit.shop.R

@Composable
fun SignUpInformation(navController: NavController, backStackEntry: NavBackStackEntry) {
    val firstName by remember { mutableStateOf(backStackEntry.arguments?.getString("firstName") ?: "") }
    val lastName by remember { mutableStateOf(backStackEntry.arguments?.getString("lastName") ?: "") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF7F8F9),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
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

        Button(
            onClick = {

                navController.navigate("sign_up_screen?firstName=$firstName&lastName=$lastName&phoneNumber=$phoneNumber&email=$email&gender=$gender&birthDate=$birthDate")
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 130.dp)
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
                .offset(y = (-60).dp)
                .padding(start = 100.dp)
                .clickable { navController.navigate("sign_in") },
        )
    }
}
