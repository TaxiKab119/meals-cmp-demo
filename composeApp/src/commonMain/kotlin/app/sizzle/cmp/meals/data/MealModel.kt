package app.sizzle.cmp.meals.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealsResponse(
    val meals: List<Meal>
)

@Serializable
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

@Serializable
data class MealDetailsResponse(
    @SerialName("meals") val mealDetails: List<MealDetails>
)

@Serializable
data class MealDetails(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String,
    val strIngredient1: String? = null,
    val strIngredient2: String? = null,
    val strIngredient3: String? = null,
    val strIngredient4: String? = null,
    val strIngredient5: String? = null,
    val strIngredient6: String? = null,
    val strIngredient7: String? = null,
    val strIngredient8: String? = null,
    val strIngredient9: String? = null,
)