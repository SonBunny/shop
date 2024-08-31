package com.mit.shop.view.product

import com.mit.shop.R

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val image: Int,
    val isNew: Boolean,
    val colors: List<String>,
    val price: Double
) {
    companion object {
        val products = listOf(
            Product(1, "Nike React Miler", "Description 1", R.drawable.product1, true, listOf("Red", "Blue", "Green"), 120.99),
            Product(2, "Nike Air Max 270", "Description 2", R.drawable.product2, true, listOf("Yellow", "Black"), 119.99),
            Product(3, "Nike Air Max 2090", "Description 3", R.drawable.product3, true, listOf("Purple", "Orange"), 99.99)
            // Add more products as needed
        )
    }
}