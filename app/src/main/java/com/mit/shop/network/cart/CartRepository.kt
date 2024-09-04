package com.mit.shop.network.cart

import ApiService

import com.mit.shop.model.CartResponse
import com.mit.shop.model.CartModel
import com.mit.shop.model.AccountCart
import retrofit2.Response

class CartRepository(private val apiService: ApiService) {

    suspend fun getCartItems(accountId: Int): Response<List<CartResponse>> {
        return apiService.getCartItems(accountId)
    }



    suspend fun addToCart(
        productId: Int,
        quantity: Int,
        accountId: Int,
        selectedSize: String?,
        selectedColor: String?
    ): Response<Map<String, Any>> {
        return apiService.addToCart(productId, quantity, accountId, selectedSize, selectedColor)
    }

    suspend fun updateCartItem(
        cartId: Int,
        quantity: Int,
        selectedSize: String?,
        selectedColor: String?
    ): Response<Map<String, Any>> {
        return apiService.updateCartItem(cartId, quantity, selectedSize, selectedColor)
    }



    suspend fun deleteCartItems(cartIds: List<Int>): Response<Map<String, Any>> {

        val idsCsv = cartIds.joinToString(separator = ",")

        try {

            val response = apiService.deleteCartItems(idsCsv)

            if (response.isSuccessful) {

                println("Items deleted successfully")
            } else {

                println("Error deleting items: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {

            println("Exception occurred: ${e.message}")
        }

        return apiService.deleteCartItems(idsCsv)
    }

}
