package com.mit.shop.view.product

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mit.shop.R
import com.mit.shop.decodeBase64ToImageBitmap
import com.mit.shop.model.ProductModel

import kotlinx.coroutines.delay



@Composable
fun ProductCard(
    navController: NavController,
    product: ProductModel,
    totalProducts: Int,
    currentIndex: Int,
    onDotClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = modifier
            .padding(25.dp)
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                val imageBitmap = product.image?.let { decodeBase64ToImageBitmap(it) }
                Image(
                    bitmap = imageBitmap ?: ImageBitmap.imageResource(id = R.drawable.product1),
                    contentDescription = product.name,
                    modifier = Modifier
                        .height(120.dp)
                        .width(180.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(modifier = Modifier.padding(start = 17.dp, top = 5.dp)) {
                    Text(
                        text = "Popular",
                        style = MaterialTheme.typography.h6,
                        fontSize = 13.sp,
                        modifier = Modifier
                    )
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.h6,
                        fontSize = 15.sp,
                        modifier = Modifier,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = { navController.navigate("product_detail_screen/${product.id}") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3E4958),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .size(width = 80.dp, height = 40.dp)

                    ) {

                    }
                    Text(
                        text = "Buy now",
                        modifier = Modifier
                            .padding(start=15.dp)
                            .offset(y = (-29).dp),
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))


            DotsIndicator(
                totalDots = totalProducts,
                selectedIndex = currentIndex,
                onDotClicked = onDotClicked
            )
        }
    }
}






@Composable
fun DotsIndicator(totalDots: Int, selectedIndex: Int, onDotClicked: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in 0 until totalDots) {
            val color = if (i == selectedIndex) Color.Black else Color.Gray
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
                    .clickable { onDotClicked(i) }
            )
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProductShowcase(navController: NavController, products: List<ProductModel>) {
    var currentProductIndex by remember { mutableStateOf(0) }


    LaunchedEffect(currentProductIndex) {
        delay(3000)
        currentProductIndex = (currentProductIndex + 1) % products.size
    }

    val currentProduct = products[currentProductIndex]

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedContent(
            targetState = currentProduct,
            transitionSpec = {
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn() with slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
            }
        ) { product ->
            ProductCard(
                navController = navController,
                product = product,
                totalProducts = products.size,
                currentIndex = currentProductIndex,
                onDotClicked = { index -> currentProductIndex = index }
            )
        }
    }
}
