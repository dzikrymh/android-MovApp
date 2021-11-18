package me.dzikry.movapp.data.network

import me.dzikry.movapp.utils.Tools
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Tools.BASE_URL_MOVIE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}