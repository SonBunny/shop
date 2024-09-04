package com.mit.shop.view.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mit.shop.R
import com.mit.shop.decodeBase64ToImageBitmap
import com.mit.shop.model.ProductModel
import com.mit.shop.network.cart.CartRepository
import com.mit.shop.network.product.ProductRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int,
    cartRepository: CartRepository,
    accountId: Int
) {
    val productRepository = ProductRepository();
    var product by remember { mutableStateOf<ProductModel?>(null) }
    var selectedSize by remember { mutableStateOf<String?>(null) }
    var selectedColor by remember { mutableStateOf<String?>(null) }
    var quantity by remember { mutableStateOf(1) }
    var showMessage by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var showError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()


    LaunchedEffect(productId) {
        try {
            product = productRepository.fetchProductDetails(productId)
        } catch (e: Exception) {
            showError = "Failed to load product details."
        } finally {
            isLoading = false
        }
    }


    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }


    if (product == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = showError ?: "Product not found", color = Color.Red)
        }
        return
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back"
            )
        }


        val imageBitmap = product!!.image?.let { decodeBase64ToImageBitmap(it) }
        Image(
            bitmap = imageBitmap ?: ImageBitmap.imageResource(id = R.drawable.product1),
            contentDescription = product!!.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = product!!.name,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "$${product!!.price}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Green
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text("Select Size:", style = MaterialTheme.typography.titleSmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            product!!.sizes.forEach { size ->
                Button(
                    onClick = { selectedSize = size },
                    colors = if (selectedSize == size) ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    ) else ButtonDefaults.buttonColors()
                ) {
                    Text(text = size)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text("Select Color:", style = MaterialTheme.typography.titleSmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            product!!.colors.forEach { color ->
                Button(
                    onClick = { selectedColor = color },
                    colors = if (selectedColor == color) ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    ) else ButtonDefaults.buttonColors()
                ) {
                    Text(text = color)
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text("Categories:", style = MaterialTheme.typography.bodySmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            product!!.categories.forEach { category ->
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray)
                        .padding(8.dp)
                        .background(Color.White)
                ) {
                    Text(
                        text = category,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp) // Padding inside the Text
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))


        Text("Select Quantity:", style = MaterialTheme.typography.titleSmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { if (quantity > 1) quantity-- },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text("-", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text("$quantity", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { quantity++ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text("+", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        if (showMessage) {
            Text(
                text = "Please select both size and color.",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }


        Button(
            onClick = {
                if (selectedSize != null && selectedColor != null) {
                    isLoading = true
                    scope.launch {
                        try {
                            val response = cartRepository.addToCart(
                                product!!.id,
                                quantity,
                                accountId,
                                selectedSize!!,
                                selectedColor!!
                            )
                            isLoading = false
                            if (response.isSuccessful) {
                                navController.popBackStack() // Navigate back to the previous screen
                            } else {
                                showError = "Failed to add item to cart. Please try again."
                            }
                        } catch (e: Exception) {
                            isLoading = false
                            showError = "An error occurred. Please try again."
                        }
                    }
                } else {
                    showMessage = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            enabled = !isLoading && selectedSize != null && selectedColor != null
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
            } else {
                Text("Add to Cart", color = Color.White, style = MaterialTheme.typography.bodyLarge)
            }
        }


        if (showError != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = showError!!,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}
