package com.mit.shop.view.product

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*

import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CardDefaults

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun CarouselProduct(navController: NavController, products: List<Product>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product,navController)
        }
    }
}



@Composable
fun ProductCard(product: Product,navController: NavController) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .size(height = 270.dp, width = 180.dp), // Adjust card size
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFFD5DDE0))

                .size(height = 25.dp, width = 80.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "${product.colors.size} colors",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth(),

            ) {
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1, // Ensures the text is constrained to one line
                    overflow = TextOverflow.Ellipsis // Adds ellipsis (...) if the text is too long
                )

                Spacer(modifier = Modifier.width(50.dp))

                IconButton(
                    onClick = {
                        // Handle add to cart action
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp)),

                    content = {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Add to Cart",
                            tint = Color.Gray
                        )
                    }
                )
            }

        }
    }
}

