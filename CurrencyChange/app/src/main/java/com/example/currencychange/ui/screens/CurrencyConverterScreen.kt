package com.example.currencychallenge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencychallenge.ui.components.DropdownMenuComponent
import com.example.currencychallenge.ui.viewmodel.CurrencyViewModel
import java.time.format.TextStyle

@Composable
fun CurrencyConverterScreen(viewModel: CurrencyViewModel) {
    var baseCurrency by remember { mutableStateOf("USD") }
    var targetCurrency by remember { mutableStateOf("EUR") }
    var amount by remember { mutableStateOf("") }
    var convertedAmount by remember { mutableStateOf<Double?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val exchangeRates by viewModel.exchangeRates.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB3E5FC)) // Light blue background
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Styled title
            Text(
                text = "Currency Exchallenge",
                fontSize = 50.sp, // Larger text
                fontStyle = FontStyle.Italic, // Italic style
                fontWeight = FontWeight.Bold, // Bold text
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Base Currency:",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                DropdownMenuComponent(
                    selectedCurrency = baseCurrency,
                    onCurrencySelected = {
                        baseCurrency = it
                        viewModel.fetchExchangeRates(it)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = amount,
                    onValueChange = { input ->
                        if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d*\$"))) {
                            amount = input
                        }
                    },
                    label = { Text("Amount in $baseCurrency") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Convert to:",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                DropdownMenuComponent(
                    selectedCurrency = targetCurrency,
                    onCurrencySelected = { targetCurrency = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                } else {
                    Button(
                        onClick = {
                            val inputValue = amount.toDoubleOrNull()
                            val rate = exchangeRates[targetCurrency] ?: 1.0
                            convertedAmount = inputValue?.times(rate)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = exchangeRates.isNotEmpty()
                    ) {
                        Text("Convert", fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                errorMessage?.let {
                    Text(
                        text = "⚠️ $it",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                convertedAmount?.let {
                    Text(
                        text = "Equivalent in $targetCurrency: %.2f".format(it),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00796B) // Dark green
                    )
                }
            }
        }
    }
}