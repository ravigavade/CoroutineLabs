package com.csaim.coroutinelabs

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

suspend fun fetchTemperature(city: City): Double = withContext(Dispatchers.IO) {
    val url = "https://api.open-meteo.com/v1/forecast?latitude=${city.lat}&longitude=${city.lon}&current=temperature_2m"
    val text = URL(url).readText()
    val json = JSONObject(text)
     json.getJSONObject("current").getDouble("temperature_2m")

}