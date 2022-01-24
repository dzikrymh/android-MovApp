package me.dzikry.movapp.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface AuthAPIs {

    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Any

    companion object {
        operator fun invoke() : AuthAPIs {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://192.168.1.8:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthAPIs::class.java)
        }
    }

}