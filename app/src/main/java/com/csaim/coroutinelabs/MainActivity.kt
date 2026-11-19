package com.csaim.coroutinelabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.csaim.coroutinelabs.ui.theme.CoroutineLabsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutineLabsTheme {
                val viewModel: WeatherViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherScreen(viewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }
}