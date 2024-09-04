package com.mit.shop.view.nav

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        placeholder = {
            Text(
                text = "Search",
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(start = 45.dp, end = 45.dp, bottom = 40.dp, top = 30.dp)
            .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFF7F8F9),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { /* Handle search action if needed */ }),
        singleLine = true // Ensure the text input stays on a single line
    )
}