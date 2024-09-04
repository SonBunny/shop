package com.mit.shop.network.account

import android.util.Log
import com.mit.shop.model.AccountModel
import com.mit.shop.model.AccountModelResponse

import com.mit.shop.model.SignUpStatusResponse
import com.mit.shop.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountRepository {

    private val apiService = ApiService.create()


    fun authenticateUser(email: String, password: String, callback: (AccountModelResponse?) -> Unit) {
        val call = apiService.checkConnection(email, password)
        call.enqueue(object : Callback<AccountModelResponse> {
            override fun onResponse(call: Call<AccountModelResponse>, response: Response<AccountModelResponse>) {
                Log.d("AccountRepository", "Raw response: ${response.raw()}")
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.status == "success") {
                        Log.d("AccountRepository", "Authentication successful: $apiResponse")
                        callback(apiResponse)
                    } else {
                        Log.d("AccountRepository", "Authentication failed: ${apiResponse?.message}")
                        callback(null)
                    }
                } else {
                    Log.d("AccountRepository", "HTTP error: ${response.code()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<AccountModelResponse>, t: Throwable) {
                Log.d("AccountRepository", "Network failure: ${t.localizedMessage}")
                callback(null)
            }
        })
    }


    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String?,
        gender: String?,
        birthDate: String?
    ): SignUpStatusResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.signUp(email, password, firstName, lastName, phoneNumber, gender, birthDate)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {

                        Log.d("AccountRepository", "Sign up response: $it")
                        return@withContext it
                    }
                } else {
                    Log.d("AccountRepository", "Sign up HTTP error: ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                Log.d("AccountRepository", "Sign up failed: ${e.localizedMessage}")
                null
            }
        }
    }
}
