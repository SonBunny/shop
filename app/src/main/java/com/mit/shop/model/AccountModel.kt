package com.mit.shop.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

data class AccountModel(
    val id: Int,
    val email: String,
    val password: String
)

class AccountModelDeserializer : JsonDeserializer<AccountModel> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): AccountModel {
        val jsonObject = json.asJsonObject


        val id = jsonObject.get("id").asInt
        val email = jsonObject.get("email")?.asString ?: ""
        val password = jsonObject.get("password")?.asString ?: ""

        return AccountModel(id, email, password)
    }
}


data class UserModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val gender: String,
    val birthDate: String,
    val accountId: Int
)

data class AccountModelResponse(
    val id: Int,
    val status: String,
    val message: String? = null
)

data class SignUpStatusResponse(
    val status: String,
    val message: String? = null
)

