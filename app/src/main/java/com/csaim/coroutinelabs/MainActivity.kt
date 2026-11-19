package com.csaim.coroutinelabs

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.csaim.coroutinelabs.ui.theme.CoroutineLabsTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch{

//            logTime("10 cities, blocking") {
            logTime("11 cities, parallel with 1 broken city") {

//                val results = cities.map { city ->
//                    async{
//
//                        try {
//                            city to fetchTemperature(city)
//                        } catch (e: Exception) {
//                            Log.e("CoroutineLabs", "${city.name} failed: ${e.message}")
//                            city to null
//                        }
//                    }
//
//                }.awaitAll()
//
//                for ((city, temp) in results) {
//                    if (temp != null) {
//                        Log.d("CoroutineLabs", "${city.name}: $temp °C")
//                    } else {
//                        Log.d("CoroutineLabs", "${city.name}: no data")
//                    }
//                }

                supervisorScope {
                    val requests = cities.map { city ->
                        city to async { fetchTemperature(city) }
                    }

                    for ((city, request) in requests) {
                        try {
                            Log.d("CoroutineLabs", "${city.name}: ${request.await()} °C")
                        } catch (e: Exception) {
                            Log.e("CoroutineLabs", "${city.name}: failed (${e.message})")
                        }
                    }



                }
            }
        }

//        logTime("test",){ Thread.sleep(500) }


        enableEdgeToEdge()
        setContent {
            CoroutineLabsTheme {
            }
        }
    }
}
