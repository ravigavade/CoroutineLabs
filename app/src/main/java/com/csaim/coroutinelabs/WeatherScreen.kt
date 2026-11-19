package com.csaim.coroutinelabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherScreen(viewModel: WeatherViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Weather", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            if (viewModel.isLoading && viewModel.weather.isEmpty()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    items(viewModel.weather) { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(item.name, style = MaterialTheme.typography.bodyLarge)
                            Text(
                                text = if (item.temp != null) "${item.temp} °C" else "unavailable",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        HorizontalDivider()



                    }
                }
            }
        }

        viewModel.loadTimeMs?.let {
            Text("Updated in $it ms", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { viewModel.refresh() },
//            enabled = !viewModel.isLoading
        ) {
            Text("Refresh")
        }
    }
}