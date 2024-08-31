package com.mit.shop.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mit.shop.R

@Composable
fun SignUpVerify(navController: NavController){

    val focusRequesters = remember { List(4) { FocusRequester() } }

    // State to hold the values of each TextField
    var codeState by remember { mutableStateOf(List(4) { "" }) }

    // Function to handle text input and update the state
    fun onValueChange(newValue: String, index: Int) {
        if (newValue.length <= 1) {
            codeState = codeState.toMutableList().apply {
                this[index] = newValue
            }
            // Move focus to the next TextField
            if (newValue.isNotEmpty() && index < codeState.size - 1) {
                focusRequesters[index + 1].requestFocus()
            }
        }
    }



    Column(

    ) {
        Row(
            modifier = Modifier
            .padding(top = 60.dp, start = 20.dp
            )){

            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back"
                )
            }

        }

        Text(
            text = "Please check your\nemail",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFF3E4958),
            modifier = Modifier
                .padding(top = 12.dp, start = 35.dp)
        )

        Text(
            text = "We've sent you a code to your email",
            style = TextStyle(
                fontSize = 20.sp,
            ),
            color = Color(0xFF3E4958),
            modifier = Modifier
                .padding(top = 10.dp, start = 35.dp)
        )





        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(start = 23.dp)
        ){



            repeat(4) { index ->


                // Create a TextField for each digit
                TextField(
                    value = codeState[index],
                    onValueChange = { newValue ->
                        onValueChange(newValue, index)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .focusRequester(focusRequesters[index])
                        .size(80.dp) // Size of each TextField
                        .border(1.dp, Color(0xFF000000), shape = RoundedCornerShape(13.dp))
                        .padding(end = if (index < 5) 16.dp else 0.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 30.sp, color = Color.Black, fontWeight = FontWeight.Bold),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFF7F8F9), // Background color of the text field
                        focusedIndicatorColor = Color.Transparent, // Hide the focused indicator
                        unfocusedIndicatorColor = Color.Transparent // Hide the unfocused indicator
                    ),
                    singleLine = true,
                    maxLines = 1

                )
                if (index < 5) {
                    // Add space between TextFields
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
        }

        Button(
            onClick = { navController.navigate("sign_up_pass") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Center button horizontally
                .padding(vertical = 120.dp)
                .size(height = 50.dp, width = 300.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Verify")
        }






    }
}