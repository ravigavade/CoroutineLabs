package com.csaim.coroutinelabs

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withTimeoutOrNull

data class CityWeather(val name: String, val temp: Double?)

class WeatherViewModel : ViewModel() {

    var weather by mutableStateOf<List<CityWeather>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var loadTimeMs by mutableStateOf<Long?>(null)
        private set

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading = true
            val start = System.currentTimeMillis()

            weather = supervisorScope {
                val requests = cities.map { city ->
//                    city to async { fetchTemperature(city) }
                    city to async {
                        val temp = withTimeoutOrNull(3_000) { fetchTemperature(city) }
                        if (temp == null) Log.w("CoroutineLabs", "${city.name} timed out")
                        temp
                    }

                }
                requests.map { (city, request) ->
                    val temp = try {
                        request.await()
                    } catch (e: Exception) {
                        Log.e("CoroutineLabs", "${city.name} failed: ${e.message}")
                        null
                    }
                    CityWeather(city.name, temp)
                }
            }

            loadTimeMs = System.currentTimeMillis() - start
            isLoading = false
        }
    }
}