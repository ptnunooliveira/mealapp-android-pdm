package upca.example.mealapp.utils

import org.json.JSONObject

fun JSONObject.optStringOrNull(key : String) : String? {

    if(isNull(key)) return null
    val value = getString(key)
    if(value.isBlank()) return null
    return value
}