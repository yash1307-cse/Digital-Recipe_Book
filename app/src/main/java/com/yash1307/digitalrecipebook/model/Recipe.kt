package com.yash1307.digitalrecipebook.model

data class Recipe(
    val calories: Double,
    val dietLabels: List<String>,
    val healthLabels: List<String>,
    val image: String,
    val label: String,
    val totalNutrients: TotalNutrients,
)