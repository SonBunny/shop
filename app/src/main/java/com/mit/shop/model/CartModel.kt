package com.mit.shop.model

import java.math.BigDecimal

data class CartModel(
    val id: Int,
    val productId: Int,
    val quantity: Int,
    val selectedSize: String?,
    val selectedColor: String?
)

data class AccountCart(
    val accountId: Int,
    val cartId: Int
)

data class CartResponse(
    val id: Int,
    val name: String,
    val image: String,  // Base64 string
    val isNew: Boolean,
    val price: BigDecimal,
    val salePercentage: BigDecimal,
    val quantity: Int,
    val selectedSize: String?,
    val selectedColor: String?
)

data class CartApiResponse(
    val id: Int,
    val message: String?,
    val error:String?
)
