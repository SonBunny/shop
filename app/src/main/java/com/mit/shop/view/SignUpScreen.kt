package com.mit.shop.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mit.shop.R
import com.mit.shop.network.account.AccountRepository
import com.mit.shop.model.SignUpStatusResponse
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavController, backStackEntry: NavBackStackEntry) {

    val firstName by remember { mutableStateOf(backStackEntry.arguments?.getString("firstName") ?: "") }
    val lastName by remember { mutableStateOf(backStackEntry.arguments?.getString("lastName") ?: "") }
    val phoneNumber by remember { mutableStateOf(backStackEntry.arguments?.getString("phoneNumber") ?: "") }
    val email by remember { mutableStateOf(backStackEntry.arguments?.getString("email") ?: "") }
    val gender by remember { mutableStateOf(backStackEntry.arguments?.getString("gender") ?: "") }
    val birthDate by remember { mutableStateOf(backStackEntry.arguments?.getString("birthDate") ?: "") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var signUpStatus by remember { mutableStateOf<SignUpStatusResponse?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val accountRepository = remember { AccountRepository() }
    val coroutineScope = rememberCoroutineScope()

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
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password1!") },
            visualTransformation = PasswordVisualTransformation(),
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
            text = "Confirm Password",
            fontSize = 15.sp,
            color = Color(0xFF3E4958),
            modifier = Modifier
                .padding(top = 7.dp, start = 35.dp)
        )

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = { Text("Confirm your password") },
            visualTransformation = PasswordVisualTransformation(),
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

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                if (password == confirmPassword) {
                    coroutineScope.launch {
                        isLoading = true
                        val response = accountRepository.signUp(
                            email,
                            password,
                            firstName,
                            lastName,
                            phoneNumber,
                            gender,
                            birthDate
                        )
                        isLoading = false
                        if (response?.status == "success") {
                            navController.navigate("sign_up_success")
                        } else {

                            signUpStatus = response
                        }
                    }
                } else {

                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 120.dp)
                .size(height = 50.dp, width = 300.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Sign Up")
            }
        }

        signUpStatus?.let { status ->
            Text(
                text = "Sign Up Status: ${status.message}",
                color = if (status.status == "success") Color.Green else Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
