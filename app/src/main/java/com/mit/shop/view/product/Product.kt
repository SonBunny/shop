package com.mit.shop.view.product

// Data class for Product
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val image: Int,
    val isNew: Boolean,
    val colors: List<String>, // Array of colors as a list of strings
    val price: Double
)

