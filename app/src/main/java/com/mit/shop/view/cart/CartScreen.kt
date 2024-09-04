package com.mit.shop.view.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mit.shop.R
import com.mit.shop.SessionManager
import com.mit.shop.decodeBase64ToImageBitmap
import com.mit.shop.model.CartResponse
import com.mit.shop.network.cart.CartRepository
import com.mit.shop.view.nav.SearchBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    cartRepository: CartRepository,
    sessionManager: SessionManager
) {

    var cartItems by remember { mutableStateOf<List<CartResponse>>(emptyList()) }
    var totalAmount by remember { mutableStateOf(0.0) }
    var searchText by remember { mutableStateOf("") }
    var selectedItems by remember { mutableStateOf(setOf<Int>()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        isLoading = true
        val accountId = sessionManager.getUserId()
        val response = cartRepository.getCartItems(accountId)
        if (response.isSuccessful) {
            cartItems = response.body() ?: emptyList()
            totalAmount = calculateTotalAmount(cartItems)
        }
        isLoading = false
    }

    val filteredCartItems = cartItems.filter { it.name.contains(searchText, ignoreCase = true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Total:",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "$${totalAmount.format(2)}", // Format to 2 decimal places
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f)) // Spacer to push buttons to the right
                    Button(
                        onClick = {
                            scope.launch {
                                val selectedIds = selectedItems.toList()
                                val response = cartRepository.deleteCartItems(selectedIds)
                                if (response.isSuccessful) {
                                    val accountId = sessionManager.getUserId()
                                    val updatedResponse = cartRepository.getCartItems(accountId)
                                    if (updatedResponse.isSuccessful) {
                                        cartItems = updatedResponse.body() ?: emptyList()
                                        selectedItems = emptySet()
                                        totalAmount = calculateTotalAmount(cartItems)
                                    } else {
                                        // Handle error, possibly show a toast or snackbar
                                        println("Error fetching updated cart items: ${updatedResponse.errorBody()}")
                                    }
                                } else {
                                    // Handle error, possibly show a toast or snackbar
                                    println("Error deleting cart items: ${response.errorBody()}")
                                }
                            }

                        },
                        enabled = selectedItems.isNotEmpty()
                    ) {
                        Text("Delete Selected")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { navController.navigate("order_success") },
                        enabled = cartItems.isNotEmpty() // Enable only if there are items in the cart
                    ) {
                        Text("Checkout")
                    }
                }
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
                SearchBar(searchText) { searchText = it }
                LazyColumn {
                    items(filteredCartItems) { item ->
                        CartItemCard(
                            cartItem = item,
                            isSelected = selectedItems.contains(item.id),
                            onSelect = { isChecked ->
                                selectedItems = if (isChecked) {
                                    selectedItems + item.id
                                } else {
                                    selectedItems - item.id
                                }
                            },
                            onQuantityChange = { id, newQuantity ->
                                scope.launch {
                                    val response = cartRepository.updateCartItem(id, newQuantity, item.selectedSize, item.selectedColor )
                                    if (response.isSuccessful) {
                                        val accountId = sessionManager.getUserId()
                                        val updatedResponse = cartRepository.getCartItems(accountId)
                                        if (updatedResponse.isSuccessful) {
                                            cartItems = updatedResponse.body() ?: emptyList()
                                            totalAmount = calculateTotalAmount(cartItems)
                                        } else {

                                        }
                                    } else {

                                    }
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartResponse,
    isSelected: Boolean,
    onSelect: (Boolean) -> Unit,
    onQuantityChange: (Int, Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp, bottom = 10.dp, top = 10.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
                .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(12.dp))
        ) {
            val imageBitmap = cartItem.image?.let { decodeBase64ToImageBitmap(it) }
            Image(
                bitmap = imageBitmap ?: ImageBitmap.imageResource(id = R.drawable.product1),
                contentDescription = cartItem.name,
                modifier = Modifier
                    .size(110.dp)
                    .padding(10.dp)
                    .border(1.dp, Color(0xFFD5DDE0), shape = RoundedCornerShape(6.dp))
                    .clip(RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f).padding(top = 10.dp)) {
                Text(
                    text = cartItem.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row {
                    Text(text = "$${cartItem.price}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    cartItem.selectedSize?.let { size ->
                        Text(text = "Size: $size", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 4.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    cartItem.selectedColor?.let { color ->
                        Text(text = "Color: $color", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 4.dp))
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Button(
                        onClick = {
                            val newQuantity = (cartItem.quantity - 1).coerceAtLeast(1)
                            onQuantityChange(cartItem.id, newQuantity)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent
                        ),
                    ) {
                        Text(text = "-", color = Color.Black, style = MaterialTheme.typography.bodyLarge)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${cartItem.quantity}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val newQuantity = cartItem.quantity + 1
                            onQuantityChange(cartItem.id, newQuantity)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent
                        ),
                    ) {
                        Text(text = "+", color = Color.Black, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onSelect(it) }
            )
        }
    }
}

fun calculateTotalAmount(cartItems: List<CartResponse>): Double {
    return cartItems.sumOf { it.price.toDouble() * it.quantity }
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)


