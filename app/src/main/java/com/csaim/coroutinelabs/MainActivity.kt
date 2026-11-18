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
import com.csaim.coroutinelabs.ui.theme.CoroutineLabsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread{

            logTime("10 cities, blocking") {
                for (city in cities) {
                    val temp = fetchTemperature(city)
                    Log.d("CoroutineLabs", "${city.name}: $temp °C")
                }
            }
        }.start()

//        logTime("test",){ Thread.sleep(500) }


        enableEdgeToEdge()
        setContent {
            CoroutineLabsTheme {
            }
        }
    }
}
