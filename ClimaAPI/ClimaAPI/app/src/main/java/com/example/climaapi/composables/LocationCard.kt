package com.example.climaapi.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LocationCard(
    modifier: Modifier,
    label: String,
    geo: String
){
    Card (
        modifier = Modifier
            .then(
                Modifier
                    .padding(8.dp)

            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        )
    ){
Text(
    modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
    textAlign = TextAlign.Center,
    text = "$label\n$geo",
    fontSize = 12.sp
)
    }
}