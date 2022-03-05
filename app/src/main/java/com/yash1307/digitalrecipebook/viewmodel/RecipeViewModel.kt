package com.yash1307.digitalrecipebook.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yash1307.digitalrecipebook.api.RetrofitInstance
import com.yash1307.digitalrecipebook.model.Recipes
import com.yash1307.digitalrecipebook.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class RecipeViewModel : ViewModel() {

    private val recipeRepository: RecipeRepository =
        RecipeRepository(RetrofitInstance.provideApiService)

    private val recipeMutableLiveData: MutableLiveData<Recipes> = MutableLiveData()
    private val singleRecipeMutableLiveData: MutableLiveData<Recipes> = MutableLiveData()

    val recipeLiveData: LiveData<Recipes>
        get() = recipeMutableLiveData

    val singleRecipeLiveData: LiveData<Recipes>
    get() = singleRecipeMutableLiveData

    fun getRecipes(q: String) = viewModelScope.launch {
        recipeRepository.getRecipes(q).catch { exception ->
            Log.d("ERROR", "$exception.localizedMessage")
        }.collect { recipes ->
            recipeMutableLiveData.value = recipes
        }
    }

    fun getSingleRecipe(q:String) = viewModelScope.launch {
        recipeRepository.getSingleRecipe(q).catch { exception ->
            Log.d("ERROR", "$exception.localizedMessage")
        }.collect { recipes ->
            singleRecipeMutableLiveData.value = recipes
        }
    }
}
