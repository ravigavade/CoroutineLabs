package com.csaim.coroutinelabs

import org.json.JSONObject
import java.net.URL

fun fetchTemperature(city: City): Double {
    val url = "https://api.open-meteo.com/v1/forecast?latitude=${city.lat}&longitude=${city.lon}&current=temperature_2m"
    val text = URL(url).readText()
    val json = JSONObject(text)
    return json.getJSONObject("current").getDouble("temperature_2m")

}