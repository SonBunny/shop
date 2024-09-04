package com.mit.shop.view.product

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


import com.mit.shop.R
import com.mit.shop.decodeBase64ToImageBitmap
import com.mit.shop.model.ProductModel
import com.mit.shop.network.product.ProductRepository


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavController, category: String?) {
    var products by remember { mutableStateOf<List<ProductModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(category) {
        try {
            products = ProductRepository().fetchProducts() ?: emptyList()

            Log.d("ProductListScreen", "Products fetched successfully: $products")
        } catch (e: Exception) {
            error = "Failed to load products. Please try again."
            Log.e("ProductListScreen", "Error fetching products", e)
        } finally {
            isLoading = false
            Log.d("ProductListScreen", "Loading finished")
        }
    }

    var searchText by remember { mutableStateOf("") }
    var selectedCategories by remember { mutableStateOf(setOf<String>()) }
    var selectedSortOrder by remember { mutableStateOf(SortOrder.ASC) }
    var currentPage by remember { mutableStateOf(1) }
    var showFilterModal by remember { mutableStateOf(false) }
    var tempSelectedCategories by remember { mutableStateOf(setOf<String>()) }
    var tempSortOrder by remember { mutableStateOf(SortOrder.ASC) }

    val itemsPerPage = 5


    val filteredProducts = remember(
        searchText,
        selectedCategories,
        tempSortOrder,
        products,
        category
    ) {
        products
            .filter { it.name.contains(searchText, ignoreCase = true) }
            .filter {
                (selectedCategories.isEmpty() || it.categories.any { cat -> selectedCategories.contains(cat) }) &&
                        (category == null || it.categories.contains(category))
            }
            .sortedWith(
                compareBy { if (tempSortOrder == SortOrder.ASC) it.price else -it.price }
            )
    }

    Log.d("ProductListScreen", "Search Text: $searchText")
    Log.d("ProductListScreen", "Selected Categories: $selectedCategories")
    Log.d("ProductListScreen", "Category: $category")
    Log.d("ProductListScreen", "Filtered Products: $filteredProducts")
    Log.d("ProductListScreen", "Sort Order: $tempSortOrder")

    val totalPages = (filteredProducts.size + itemsPerPage - 1) / itemsPerPage


    val paginatedProducts = if (filteredProducts.isEmpty()) {
        products.drop((currentPage - 1) * itemsPerPage).take(itemsPerPage)
    } else {
        filteredProducts.drop((currentPage - 1) * itemsPerPage).take(itemsPerPage)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showFilterModal = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Filter")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (isLoading) {

                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (error != null) {

                Text(
                    text = error ?: "Unknown error",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {

                SearchBar(
                    searchText = searchText,
                    onSearchTextChanged = { searchText = it },
                    onSearch = { }
                )
                LazyColumn {
                    items(paginatedProducts) { product ->
                        ProductCards(product = product, navController = navController)
                    }
                }
                PaginationControls(
                    currentPage = currentPage,
                    totalPages = totalPages,
                    onPageChanged = { newPage ->
                        currentPage = newPage.coerceIn(1, totalPages)
                    }
                )
            }

            if (showFilterModal) {
                FilterModal(
                    onDismiss = { showFilterModal = false },
                    selectedCategories = tempSelectedCategories,
                    onCategoriesChanged = { tempSelectedCategories = it },
                    selectedSortOrder = tempSortOrder,
                    onSortOrderChanged = { tempSortOrder = it },
                    onApply = {
                        selectedCategories = tempSelectedCategories
                        selectedSortOrder = tempSortOrder
                        currentPage = 1
                        showFilterModal = false
                    }
                )
            }
        }
    }
}



@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        label = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onSearch() })
    )
}

@Composable
fun FilterModal(
    onDismiss: () -> Unit,
    selectedCategories: Set<String>,
    onCategoriesChanged: (Set<String>) -> Unit,
    selectedSortOrder: SortOrder,
    onSortOrderChanged: (SortOrder) -> Unit,
    onApply: () -> Unit
) {
    val allCategories = setOf("Male", "Female", "Baby", "Sales")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter and Sort") },
        text = {
            Column {
                Text("Filter by Category", style = MaterialTheme.typography.titleMedium)
                allCategories.forEach { category ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = selectedCategories.contains(category),
                            onCheckedChange = { isChecked ->
                                onCategoriesChanged(
                                    if (isChecked) selectedCategories + category
                                    else selectedCategories - category
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(category)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Sort by Price", style = MaterialTheme.typography.titleMedium)
                Row {
                    SortOrderButton(SortOrder.ASC, selectedSortOrder, onSortOrderChanged)
                    Spacer(modifier = Modifier.width(8.dp))
                    SortOrderButton(SortOrder.DESC, selectedSortOrder, onSortOrderChanged)
                }
            }
        },
        confirmButton = {
            Button(onClick = onApply) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun SortOrderButton(
    sortOrder: SortOrder,
    selectedSortOrder: SortOrder,
    onSortOrderSelected: (SortOrder) -> Unit
) {
    val icon = if (sortOrder == SortOrder.ASC) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    Button(
        onClick = { onSortOrderSelected(sortOrder) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedSortOrder == sortOrder) Color.Blue else Color.Gray,
            contentColor = Color.White
        ),
        modifier = Modifier.padding(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (sortOrder == SortOrder.ASC) "Sort: Low to High" else "Sort: High to Low"
        )
    }
}

@Composable
fun ProductCards(product: ProductModel, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {

                navController.navigate("product_detail_screen/${product.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            val imageBitmap = product.image?.let { decodeBase64ToImageBitmap(it) }
            Image(
                bitmap = imageBitmap ?: ImageBitmap.imageResource(id = R.drawable.product1),
                contentDescription = product.name,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "${product.price}$", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Composable
fun PaginationControls(
    currentPage: Int,
    totalPages: Int,
    onPageChanged: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        if (currentPage > 1) {
            Button(onClick = { onPageChanged(currentPage - 1) }) {
                Text("Previous")
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Page $currentPage of $totalPages")
        Spacer(modifier = Modifier.width(8.dp))
        if (currentPage < totalPages) {
            Button(onClick = { onPageChanged(currentPage + 1) }) {
                Text("Next")
            }
        }
    }
}