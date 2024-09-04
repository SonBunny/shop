//package com.mit.shop.view.cart
//
//import androidx.compose.runtime.mutableStateListOf
//import com.mit.shop.R
//import com.mit.shop.view.product.Product
//
//data class CartItem(
//    val id: Int,
//    val product: Product,
//    val quantity: Int,
//    val selectedSize: String?,
//    val selectedColor: String?
//) {
//    object CartManager {
//        private val _cartItems = mutableStateListOf<CartItem>()
//        val cartItems: List<CartItem> get() = _cartItems
//
//        private fun generateId(productId: Int, size: String?, color: String?): Int {
//            // Combine attributes into a unique hash code
//            return (productId.toString() + (size ?: "null") + (color ?: "null")).hashCode()
//        }
//
//        fun addToCart(product: Product, quantity: Int, selectedSize: String?, selectedColor: String?) {
//            val id = generateId(product.id, selectedSize, selectedColor)
//            val existingItem = _cartItems.find { it.id == id }
//            if (existingItem != null) {
//                _cartItems[_cartItems.indexOf(existingItem)] = existingItem.copy(quantity = existingItem.quantity + quantity)
//            } else {
//                _cartItems.add(CartItem(id, product, quantity, selectedSize, selectedColor))
//            }
//        }
//
//        fun removeItems(idsToRemove: Set<Int>) {
//            _cartItems.removeAll { it.id in idsToRemove }
//        }
//
//        fun updateQuantity(id: Int, newQuantity: Int) {
//            val existingItem = _cartItems.find { it.id == id }
//            if (existingItem != null) {
//                _cartItems[_cartItems.indexOf(existingItem)] = existingItem.copy(quantity = newQuantity)
//            }
//        }
//    }
//}
