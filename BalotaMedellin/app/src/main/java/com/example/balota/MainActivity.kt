package com.example.balota

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.BlendModeColorFilterCompat
import com.example.balota.ui.theme.BalotaTheme
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BalotaTheme {
                BalotaScreen()
                MusicPlayerScreen()
            }
        }
    }
}

@Composable
fun BalotaScreen() {
    var numerosPremio by remember { mutableStateOf(List(4) { (0..9).random() }) }
    var numerosSerie by remember { mutableStateOf(List(3) { (0..9).random() }) }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id=R.drawable.ludopatia),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)) // Ajusta la opacidad (0.0 = transparente, 1.0 = sólido)
        )


        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Baloto\n\nMedellín",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif, // Cambia la fuente aquí
                color = Color.Yellow,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center, // Centra todo verticalmente
                horizontalAlignment = Alignment.CenterHorizontally // Centra todo horizontalmente
            ) {
                // Tarjeta de números ganadores
                Card(
                    modifier = Modifier.fillMaxWidth(0.85f), // Ajusta el ancho para centrar mejor
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color(0xFF2E7D32))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Números Ganadores", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center, // Centra los números
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            numerosPremio.forEach { numero -> NumeroBola(numero) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tarjeta de números de la serie
                Card(
                    modifier = Modifier.fillMaxWidth(0.85f), // Igual que la otra tarjeta
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color(0xFF1976D2))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Números de la Serie", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            numerosSerie.forEach { numero -> NumeroBola(numero) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón centrado
                Button(
                    onClick = {
                        numerosPremio = List(4) { (0..9).random() }
                        numerosSerie = List(3) { (0..9).random() }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Asegura que el botón esté centrado
                ) {
                    Text(text = "Generar Números", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }

        }
    }
}

@Composable
fun NumeroBola(numero: Int) {
    Image(
        painter = painterResource(id = getBallImage(numero)),
        contentDescription = numero.toString(),
        modifier = Modifier.size(60.dp)
    )
}

fun getBallImage(numero: Int): Int {
    val ballImages = listOf(
        R.drawable.ball_0, R.drawable.ball_1, R.drawable.ball_2, R.drawable.ball_3,
        R.drawable.ball_4, R.drawable.ball_5, R.drawable.ball_6, R.drawable.ball_7,
        R.drawable.ball_8, R.drawable.ball_9
    )
    return ballImages.getOrElse(numero) { R.drawable.ball_0 }
}


@Composable
fun MusicPlayerScreen() {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    val currentContext by rememberUpdatedState(context)

    // Inicia la música cuando se carga el Composable
    LaunchedEffect(Unit) {
        mediaPlayer?.release() // Libera cualquier instancia previa
        mediaPlayer = MediaPlayer.create(currentContext, R.raw.jazz).apply {
            var isLooping = true // La música se repite automáticamente
            start() // Inicia la reproducción
        }
    }

    // Libera el MediaPlayer cuando el Composable se destruye
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoteriaScreen() {
    BalotaTheme {
        BalotaScreen()
    }
}
