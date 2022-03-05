package com.yash1307.digitalrecipebook.repository


import com.yash1307.digitalrecipebook.api_service_provider.ApiService
import com.yash1307.digitalrecipebook.model.Recipes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RecipeRepository(private val apiService: ApiService) {

    suspend fun getRecipes(q: String): Flow<Recipes> = flow {
        val apiResponse = apiService.getRecipes(q)
        if (apiResponse.body() != null) {
            emit(apiResponse.body()!!)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getSingleRecipe(q: String): Flow<Recipes> = flow {
        val apiResponse = apiService.getSingleRecipe(q)
        if (apiResponse.body() != null) {
            emit(apiResponse.body()!!)
        }
    }.flowOn(Dispatchers.IO)

}