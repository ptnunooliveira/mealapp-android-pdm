package upca.example.mealapp.data.model

import org.json.JSONObject
import upca.example.mealapp.utils.optStringOrNull

data class Category(

    val id: String,
    val name: String,
    val thumb: String?
) {

    companion object {

        fun fromJson(categoryJSON : JSONObject) : Category? {

            val id = categoryJSON.optStringOrNull("idCategory") ?: return null
            val name = categoryJSON.optStringOrNull("strCategory") ?: return null

            return Category(
                id = id,
                name = name,
                thumb = categoryJSON.optStringOrNull("strCategoryThumb")
            )
        }
    }
}