package com.example.climaapi.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import com.lottiefiles.dotlottie.core.util.LayoutUtil

@Composable
fun TemperatureCard(
    modifier: Modifier,
    label: String,
    temperatureValue: String,
    animationSource: String
){
    Card(
        modifier = modifier
            .then(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)

            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            text = label,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
        Row (
            modifier = Modifier
                .padding(4.dp)

        ){
            DotLottieAnimation(
                modifier = Modifier
                    .size(52.dp),
                    source = DotLottieSource.Asset("${animationSource}"),
                autoplay = true,
                loop = true,
                speed = 3f,
                useFrameInterpolation = false,
                playMode = Mode.FORWARD
            )
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .align(Alignment.CenterVertically),
                text = "${temperatureValue}°C",
                fontSize = 28.sp
            )
        }
    }
}
