package com.mit.shop.view.product

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import com.mit.shop.R
import com.mit.shop.decodeBase64ToImageBitmap
import com.mit.shop.model.ProductModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json



@Composable
fun CarouselProduct(navController: NavController, products: List<ProductModel>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product, navController = navController)
        }
    }
}


@Composable
fun ProductCard(product: ProductModel, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .size(height = 270.dp, width = 180.dp)
            .clickable {

                Log.d("Navigation", "Navigating to product detail screen: ${product.id}")
                navController.navigate("product_detail_screen/${product.id}")
            },
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            val imageBitmap = product.image?.let { decodeBase64ToImageBitmap(it) }
            Image(
                bitmap = imageBitmap ?: ImageBitmap.imageResource(id = R.drawable.product1),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFD5DDE0))
                    .size(height = 25.dp, width = 80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${product.colors.size} colors",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        // Handle add to cart logic here
                        navController.navigate("product_detail_screen/${product.id}")
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Add to Cart",
                        tint = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}


