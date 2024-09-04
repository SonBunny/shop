import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mit.shop.model.AccountModel
import com.mit.shop.model.AccountModelDeserializer
import com.mit.shop.model.AccountModelResponse
import com.mit.shop.model.CartResponse
import com.mit.shop.model.ProductModel

import com.mit.shop.model.SignUpStatusResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {



    @POST("login.php")
    @FormUrlEncoded
    fun checkConnection(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AccountModelResponse>


    @FormUrlEncoded
    @POST("createUser.php")
    suspend fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("phoneNumber") phoneNumber: String?,
        @Field("gender") gender: String?,
        @Field("birthDate") birthDate: String?
    ): Response<SignUpStatusResponse>

    @GET("get_products.php")
    suspend fun getProducts(): Response<List<ProductModel>>  // Updated return type

    @GET("get_productdetails.php")
    suspend fun getProductById(@Query("id") productId: Int): Response<ProductModel>



    @POST("get_cart.php")
    @FormUrlEncoded
    suspend fun getCartItems(@Field("account_id") accountId: Int): Response<List<CartResponse>>

    @POST("add_to_cart.php")
    @FormUrlEncoded
    suspend fun addToCart(
        @Field("product_id") productId: Int,
        @Field("quantity") quantity: Int,
        @Field("account_id") accountId: Int,
        @Field("selected_size") selectedSize: String?,
        @Field("selected_color") selectedColor: String?
    ): Response<Map<String, Any>>

    @POST("update_cart.php")
    @FormUrlEncoded
    suspend fun updateCartItem(
        @Field("cartId") cartId: Int,
        @Field("quantity") quantity: Int,
        @Field("selectedSize") selectedSize: String?,
        @Field("selectedColor") selectedColor: String?
    ): Response<Map<String, Any>>


@DELETE("delete_cart")
suspend fun deleteCartItems(@Query("ids") cartIds: String): Response<Map<String, Any>>





    companion object {
        private const val PRIMARY_BASE_URL = "http://10.103.160.80:8085/"
        private const val FALLBACK_BASE_URL = "http://10.0.2.2:8085/"

        fun create(): ApiService {

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }


            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()


            val gson: Gson = GsonBuilder()
                .registerTypeAdapter(AccountModel::class.java, AccountModelDeserializer())
                .setLenient()
                .create()

            return try {

                val retrofit = Retrofit.Builder()
                    .baseUrl(PRIMARY_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

                retrofit.create(ApiService::class.java)
            } catch (e: Exception) {

                val fallbackRetrofit = Retrofit.Builder()
                    .baseUrl(FALLBACK_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

                fallbackRetrofit.create(ApiService::class.java)
            }
        }
    }

}
