package com.mit.shop.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


data class ProductModel(
    val id: Int,
    val name: String,
    val image: String,
    val isNew: Boolean,
    val price: BigDecimal,
    val salePercentage: BigDecimal,
    val sizes: List<String>,
    val colors: List<String>,
    val categories: List<String>
)


data class ProductColor(
    val productId: Int,
    val colorId: Int
)






data class ProductCategory(
    val productId: Int,
    val categoryId: Int

)
data class ProductSize(
    val productId: Int,
    val sizeId: Int
)


data class ColorModel(
    val id: Int,
    val name: String
)

data class CategoryModel(
    val id: Int,
    val name: String
)
data class SizeModel(
    val id: Int,
    val name: String
)

