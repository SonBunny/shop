package com.mit.shop.network.product

import ApiService
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.mit.shop.model.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.*
import java.io.ByteArrayInputStream
import java.io.InputStream

class ProductRepository {

    private val apiService: ApiService = ApiService.create()

    suspend fun fetchProducts(): List<ProductModel>? {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.getProducts()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("API Error", "Error fetching products: ${response.message()}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("API Error", "Error fetching products: ${e.message}")
            null
        }
    }

    suspend fun fetchProductDetails(productId: Int): ProductModel? {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.getProductById(productId)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("API Error", "Error fetching product: ${response.message()}")
                    null
                }
            }
        } catch (e: HttpException) {
            Log.e("API Error", "HTTP Exception: ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("API Error", "Error fetching product: ${e.message}")
            null
        }
    }
}


