package com.example.climaapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climaapi.composables.LocationCard
import com.example.climaapi.composables.TemperatureCard
import com.example.climaapi.ui.theme.ClimaApiTheme
import com.example.climaapi.viewmodels.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClimaApiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val viewModel = MainViewModel()

                    TemperaturasScreen(innerPadding,viewModel)
                }
            }
        }
    }
}

@Composable
fun TemperaturasScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    val listaTemperaturas by viewModel.listaTemperaturas.collectAsState()
    val ubicacion by viewModel.ubicacion.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(), // Hace que la imagen ocupe todo el espacio disponible
            contentScale = ContentScale.Crop // Ajusta la imagen sin distorsionarla
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = ubicacion.name,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            LocationCard(
                modifier = Modifier.weight(1f),
                label = "LAT",
                geo = ubicacion.lat.toString()
            )

            LocationCard(
                modifier = Modifier.weight(1f),
                label = "LON",
                geo = ubicacion.lon.toString()
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                text = "Forecast for the next 7 days",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            LazyColumn(
                modifier = Modifier.padding(12.dp)
            ) {
                items(listaTemperaturas) { temperatura ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFB3E5FC)), // Azul claro
                        elevation = CardDefaults.elevatedCardElevation(8.dp)
                    ) {
                        Column {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                text = temperatura.time.substring(0, 10),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            ) {
                                TemperatureCard(
                                    modifier = Modifier.weight(1f),
                                    label = "Maximum temperature",
                                    temperatureValue = temperatura.values.temperatureMax.toString(),
                                    animationSource = "hot.json"
                                )

                                TemperatureCard(
                                    modifier = Modifier.weight(1f),
                                    label = "Minimum temperature",
                                    temperatureValue = temperatura.values.temperatureMin.toString(),
                                    animationSource = "cold.json"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}