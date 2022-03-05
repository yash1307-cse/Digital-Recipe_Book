package com.yash1307.digitalrecipebook.api_service_provider

import com.yash1307.digitalrecipebook.api.API_KEY
import com.yash1307.digitalrecipebook.api.APP_ID
import com.yash1307.digitalrecipebook.model.Recipe
import com.yash1307.digitalrecipebook.model.Recipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/recipes/v2?type=public&app_id=${APP_ID.appId}&app_key=${API_KEY.apiKey}")
    suspend fun getRecipes(@Query("q") q:String): Response<Recipes>

    @GET("/api/recipes/v2?type=public&app_id=${APP_ID.appId}&app_key=${API_KEY.apiKey}")
    suspend fun getSingleRecipe(@Query("q") q:String): Response<Recipes>
}