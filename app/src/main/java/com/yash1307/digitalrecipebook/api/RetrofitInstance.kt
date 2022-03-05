package com.yash1307.digitalrecipebook.api

import com.yash1307.digitalrecipebook.api_service_provider.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val provideApiService: ApiService by lazy {
        retrofitInstance.create(ApiService::class.java)
    }
}