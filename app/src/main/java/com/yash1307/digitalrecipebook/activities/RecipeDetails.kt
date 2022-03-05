package com.yash1307.digitalrecipebook.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.yash1307.digitalrecipebook.calc.*
import com.yash1307.digitalrecipebook.databinding.ActivityRecipeDetailsBinding
import com.yash1307.digitalrecipebook.model.Hit
import com.yash1307.digitalrecipebook.viewmodel.RecipeViewModel

class RecipeDetails : AppCompatActivity() {

    private lateinit var recipeName: String
    private lateinit var recipeBinding: ActivityRecipeDetailsBinding

    private lateinit var recipeViewModel: RecipeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeBinding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(recipeBinding.root)

        val extras: Bundle? = intent.extras
        if (extras != null) {
            recipeName = extras.get("recipeName").toString()
        }
        recipeViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider
                    .AndroidViewModelFactory.getInstance(application)
            )[RecipeViewModel::class.java]

        recipeViewModel.getSingleRecipe(recipeName)

        recipeViewModel.singleRecipeLiveData.observe(this, Observer { singleRecipeItem ->

            Log.d("Single Recipe Item", singleRecipeItem.hits[0].toString())
            recipeBinding.progressBar2.visibility = View.VISIBLE

            initRecipeDetails(singleRecipeItem.hits[0])

        })

    }

    @SuppressLint("SetTextI18n")
    private fun initRecipeDetails(singleRecipeDetails: Hit) {
        // load the image of recipe
        recipeBinding.singleRecipeImageView.load(singleRecipeDetails.recipe.image)

        // set name of recipes
        recipeBinding.singleRecipeNameTV.text = singleRecipeDetails.recipe.label

        //calculate the protein
        val getProtein = singleRecipeDetails.recipe.totalNutrients.PROCNT.quantity
        val setProtein = CalcProtein.calcProtein(getProtein)
        val formatProtein = String.format("%.2f", setProtein)
        recipeBinding.singleRecipeProteinTV.text = "Protein \n $formatProtein g"

        //calculate the fats
        val getFats = singleRecipeDetails.recipe.totalNutrients.FAT.quantity
        val setFats = CalcFat.calcFat(getFats)
        val formatFats = String.format("%.2f", setFats)
        recipeBinding.singleRecipeFatTV.text = "Fats \n $formatFats g"

        //calculate the carbs
        val getCarbs = singleRecipeDetails.recipe.totalNutrients.CHOCDF.quantity
        val setCarbs = CalcCarbs.calcCarbs(getCarbs)
        val formatCarbs = String.format("%.2f", setCarbs)
        recipeBinding.singleRecipeCarbTV.text = "Carbs \n $formatCarbs g"

        // set diet
        val getDiet = singleRecipeDetails.recipe.dietLabels
        if (getDiet.isEmpty()) {
            recipeBinding.singleRecipeDietMaterialView.visibility = View.GONE
            recipeBinding.singleRecipeDietTV.visibility = View.GONE
        } else {
            val containsDiet = getDiet.toString().removePrefix("[").removeSuffix("]")
            recipeBinding.singleRecipeDietTV.text = "For diet $containsDiet"
        }


        // set health
        val getHealth = singleRecipeDetails.recipe.healthLabels
        val containsHealth = getHealth.toString().removePrefix("[").removeSuffix("]")
        recipeBinding.singleRecipeHealthTV.text ="For health $containsHealth"

        //calculate the cholesterol
        val getChol = singleRecipeDetails.recipe.totalNutrients.CHOLE.quantity
        val setChol = CalcChol.calcChol(getChol)
        val formatChol = String.format("%.2f", setChol)
        recipeBinding.singleRecipeCholesterolTV.text = "Cholesterol \n $formatChol mg"

        //calculate the sodium
        val getSodium = singleRecipeDetails.recipe.totalNutrients.NA.quantity
        val setSodium = CalcNa.calcNa(getSodium)
        val formatSodium = String.format("%.2f", setSodium)
        recipeBinding.singleRecipeSodiumTV.text = "Sodium \n $formatSodium mg"

        //calculate the calcium
        val getCalcium = singleRecipeDetails.recipe.totalNutrients.CA.quantity
        val setCalcium = CalcCa.calcCa(getCalcium)
        val formatCalcium = String.format("%.2f", setCalcium)
        recipeBinding.singleRecipeCalciumTV.text = "Calcium \n $formatCalcium mg"

        //calculate the Magnesium
        val getMg = singleRecipeDetails.recipe.totalNutrients.MG.quantity
        val setMg = CalcMg.calcMg(getMg)
        val formatMg = String.format("%.2f", setMg)
        recipeBinding.singleRecipeMagnesiumTV.text = "Magnesium \n $formatMg mg"

        //calculate the potassium
        val getPotassium = singleRecipeDetails.recipe.totalNutrients.K.quantity
        val setPotassium = CalcK.calcK(getPotassium)
        val formatPotassium = String.format("%.2f", setPotassium)
        recipeBinding.singleRecipePotassiumTV.text = "Potassium \n $formatPotassium mg"

        //calculate the iron
        val getIron = singleRecipeDetails.recipe.totalNutrients.FE.quantity
        val setIron = CalcIron.calcIron(getIron)
        val formatIron = String.format("%.2f", setIron)
        recipeBinding.singleRecipeIronTV.text = "Iron \n $formatIron mg"

        //calculate the calories
        val getCalories = singleRecipeDetails.recipe.totalNutrients.ENERC_KCAL.quantity
        val setCalories = CalcEnergy.calcEnergy(getCalories)
        val formatCalories = String.format("%.2f", setCalories)
        recipeBinding.singleRecipeCalorieTV.text = "Calories \n $formatCalories kcal"

        recipeBinding.progressBar2.visibility = View.GONE

    }
}