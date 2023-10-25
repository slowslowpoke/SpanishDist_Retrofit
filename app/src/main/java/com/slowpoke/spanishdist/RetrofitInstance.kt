package com.slowpoke.spanishdist

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    const val API_KEY = "3caf5be3-7929-4f6b-93fb-af17c0ea0a64"
    private const val BASE_URL = "https://www.dictionaryapi.com/"

    val api: DictApi by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictApi::class.java)
    }
}