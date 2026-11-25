package upca.example.mealapp.data.model

import org.json.JSONObject
import upca.example.mealapp.utils.optStringOrNull

data class Meal(
    val id : String,
    val name : String?,
    val thumb : String?,
    val country : String?
){

    companion object {

        fun fromJson(mealJson : JSONObject) : Meal? {

            val id = mealJson.optStringOrNull("idMeal") ?: return null

            return Meal (
                id = id,
                name = mealJson.optStringOrNull("strMeal"),
                thumb = mealJson.optStringOrNull("strMealThumb"),
                country = mealJson.optStringOrNull("strCountry")
            )
        }
    }
}
