package com.mit.shop.view.product

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*

import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CarouselProduct(navController: NavController, products: List<Product>) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // This launches a coroutine that runs indefinitely to auto-scroll
    LaunchedEffect(key1 = listState) {
        while (true) {
            // Auto-scroll to the next item every 5 seconds
            coroutineScope.launch {
                val nextIndex = (listState.firstVisibleItemIndex + 1) % products.size
                listState.animateScrollToItem(nextIndex)
            }
            delay(5000) // Delay for 5 seconds
        }
    }

    // Add the same products to the list to create an infinite loop effect
    val extendedProductList = products + products

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(extendedProductList) { product ->
            ProductCard(product = product, navController)
        }
    }
}

@Composable
fun ProductCard(product: Product,navController: NavController) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(2.dp, if (product.isNew) Color.Red else Color.Transparent, RoundedCornerShape(16.dp)) // Highlight if new
            .clickable { /* Handle click */ }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.name,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${product.description.take(30)}...", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            if (product.isNew) {
                Text(
                    text = "New!",
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
