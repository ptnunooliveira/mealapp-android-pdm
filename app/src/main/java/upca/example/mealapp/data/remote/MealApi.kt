package upca.example.mealapp.data.remote

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject
import upca.example.mealapp.data.model.Category
import upca.example.mealapp.data.model.Meal
import upca.example.mealapp.data.model.MealDetail

object MealApi {

    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1"

    private val client = OkHttpClient()


    fun getMealsByCategory(
        category: String,
        onSuccess: (List<Meal>) -> Unit,
        onError: (String) -> Unit
    ) {

        val url = "$BASE_URL/filter.php?c=$category"

        fetch(url = url, onError = onError) { body ->
            val result = JSONObject(body)
            val mealsList = arrayListOf<Meal>()

            if(!result.isNull("meals")) {
                val mealsArray = result.getJSONArray("meals")
                for (index in 0..<mealsArray.length()) {
                    val mealJson = mealsArray.getJSONObject(index)
                    val meal = Meal.fromJson(mealJson)
                    if (meal != null) {
                        mealsList.add(meal)
                    }
                }
            }
            onSuccess(mealsList)
        }
    }


    fun getMealById(
        id : String,
        onSuccess : (MealDetail) -> Unit,
        onError: (String) -> Unit
    ) {

        val url = "$BASE_URL/lookup.php?i=$id"

        fetch(url = url, onError = onError) { body ->
            val result = JSONObject(body)
            if(!result.isNull("meals")) {
                val mealDetailArray = result.getJSONArray("meals")
                val detail = mealDetailArray.getJSONObject(0)
                val mealDetail = MealDetail.fromJson(detail)
                if (mealDetail != null) {
                    onSuccess(mealDetail)
                } else {
                    onError("Invalid meal data")
                }
            } else {
                onError("Meal doesn't exist")
            }
        }
    }


    fun getRandomMeal(
        onSuccess: (MealDetail) -> Unit,
        onError: (String) -> Unit
    ) {

        val url = "$BASE_URL/random.php"

        fetch(url = url, onError = onError) { body ->
            val result = JSONObject(body)
            if(!result.isNull("meals")) {
                val mealDetailArray = result.getJSONArray("meals")
                val detail = mealDetailArray.getJSONObject(0)
                val mealDetail = MealDetail.fromJson(detail)
                if (mealDetail != null) {
                    onSuccess(mealDetail)
                } else {
                    onError("Invalid meal data")
                }
            } else {
                onError("Meal doesn't exist")
            }
        }
    }


    fun getCategories(
        onSuccess: (List<Category>) -> Unit,
        onError: (String) -> Unit
    ) {

        val url = "$BASE_URL/categories.php"

        fetch(url = url, onError = onError) { body ->
            val result = JSONObject(body)
            val resultArray = result.getJSONArray("categories")
            val categories = arrayListOf<Category>()
            for(i in 0 ..<resultArray.length()){
                val categoryJSON = resultArray.getJSONObject(i)
                val category = Category.fromJson(categoryJSON)
                if(category != null) {
                    categories.add(category)
                }
            }

            onSuccess(categories)
        }
    }


    private fun fetch(
        url : String,
        onError: (String) -> Unit,
        onBody: (String) -> Unit
    ) {

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError("Connection error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        try {
                            onBody(response.body.string())
                        }
                        catch (e: Exception) {
                            onError("Error reading response: ${e.message}")
                        }
                    } else {
                        onError("HTTP Error ${response.code}")
                    }
                }
            }
        })
    }
}