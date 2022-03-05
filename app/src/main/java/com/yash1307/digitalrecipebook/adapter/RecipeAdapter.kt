package com.yash1307.digitalrecipebook.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import com.yash1307.digitalrecipebook.R
import com.yash1307.digitalrecipebook.activities.RecipeDetails
import com.yash1307.digitalrecipebook.model.Hit
import com.yash1307.digitalrecipebook.model.Recipe
import com.yash1307.digitalrecipebook.model.Recipes

class RecipeAdapter(private val context: Context) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {


    private val recipesList: ArrayList<Hit> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.recipe_items_layout, null, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = recipesList[position]

        holder.recipeImageView.load(currentItem.recipe.image)
        holder.recipeNameText.text = currentItem.recipe.label

        holder.recipeItemView.setOnClickListener {
            val intent = Intent(context, RecipeDetails::class.java)
            intent.putExtra("recipeName", currentItem.recipe.label)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return recipesList.size
    }

    class RecipeViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val recipeImageView: ImageView = itemView.findViewById(R.id.recipeImageView)
        val recipeNameText: TextView = itemView.findViewById(R.id.recipeNameText)
        val recipeItemView:MaterialCardView = itemView.findViewById(R.id.recipeItemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateRecipes(newRecipesList: ArrayList<Hit>){
        recipesList.clear()
        Log.d("RECIPE SIZE", "${recipesList.size}")
        recipesList.addAll(newRecipesList)
        notifyDataSetChanged()
    }
}