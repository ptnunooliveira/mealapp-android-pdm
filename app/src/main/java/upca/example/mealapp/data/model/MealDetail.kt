package upca.example.mealapp.data.model

import org.json.JSONObject
import upca.example.mealapp.utils.optStringOrNull

data class MealDetail(
    val id : String,
    val name : String?,
    val category : String?,
    val country : String?,
    val instructions : String?,
    val thumb : String?,
    val youtube : String?,
    val ingredients : List<Ingredient>,
    val source : String?
){

    companion object {

        fun fromJson(mealDetailJson : JSONObject) : MealDetail? {

            val id = mealDetailJson.optStringOrNull("idMeal") ?: return null

            val ingredients = mutableListOf<Ingredient>()
            for(i in 1..20) {
                val name = mealDetailJson.optStringOrNull("strIngredient$i")
                val measure = mealDetailJson.optStringOrNull("strMeasure$i")

                if(name != null) {
                    ingredients.add(Ingredient(name = name, measure = measure))
                }
            }

            return MealDetail(
                id = id,
                name = mealDetailJson.optStringOrNull("strMeal"),
                category = mealDetailJson.optStringOrNull("strCategory"),
                country = mealDetailJson.optStringOrNull("strCountry"),
                instructions = mealDetailJson.optStringOrNull("strInstructions"),
                thumb = mealDetailJson.optStringOrNull("strMealThumb"),
                youtube = mealDetailJson.optStringOrNull("strYoutube"),
                ingredients = ingredients,
                source = mealDetailJson.optStringOrNull("strSource")
            )
        }

    }
}


data class Ingredient(
    val name : String,
    val measure : String?
)