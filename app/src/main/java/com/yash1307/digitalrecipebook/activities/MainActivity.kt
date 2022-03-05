package com.yash1307.digitalrecipebook.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yash1307.digitalrecipebook.R
import com.yash1307.digitalrecipebook.adapter.RecipeAdapter
import com.yash1307.digitalrecipebook.databinding.ActivityMainBinding
import com.yash1307.digitalrecipebook.model.Hit
import com.yash1307.digitalrecipebook.viewmodel.RecipeViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var recipeAdapter: RecipeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

      
        mainBinding.progressBar.visibility = View.VISIBLE

        val recipeNameList = listOf("Pizza", "Burger", "Noodles", "Kaju curry", "Biryani")

        val getRecipeName = (recipeNameList.indices).random()

        recipeViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory
                    .getInstance(application)
            )[RecipeViewModel::class.java]

        mainBinding.progressBar.visibility = View.VISIBLE
        recipeViewModel.getRecipes(recipeNameList[getRecipeName])
        
        
        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mainBinding.recyclerView.hasFixedSize()
        recipeAdapter = RecipeAdapter(this)
        mainBinding.recyclerView.adapter = recipeAdapter


        recipeViewModel.recipeLiveData.observe(this, Observer { recipeItems ->
            mainBinding.progressBar.visibility = View.GONE
            recipeAdapter.updateRecipes(recipeItems.hits)
            Log.d("RESPONSE", recipeItems.toString())
            Log.d("Size of list", recipeAdapter.itemCount.toString())
        })

        searchRecipeName()

    }

    private fun searchRecipeName() {
        mainBinding.searchRecipeFabBtn.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.recipe_search_layout, null)
            val searchRecipeET = view.findViewById<EditText>(R.id.searchRecipeET)
            val searchRecipeBtn = view.findViewById<Button>(R.id.searchRecipeBtn)
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.apply {
                this.setContentView(view)
                this.show()
            }

            searchRecipeBtn.setOnClickListener {
                val recipeName = searchRecipeET.text.toString()
                searchRecipeName(recipeName, searchRecipeET, bottomSheetDialog)
            }
        }
    }

    private fun searchRecipeName(
        recipeName: String,
        searchRecipeET: EditText,
        bottomSheetDialog: BottomSheetDialog
    ) {
        if (recipeName.isEmpty()) {
            searchRecipeET.error = "Please enter recipe name"
        } else {
            recipeViewModel.getRecipes(recipeName)
            bottomSheetDialog.dismiss()
            mainBinding.progressBar.visibility = View.VISIBLE

        }
    }
}