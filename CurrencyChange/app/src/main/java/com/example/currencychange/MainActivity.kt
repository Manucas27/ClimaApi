package com.example.currencychange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.currencychallenge.ui.screens.CurrencyConverterScreen
import com.example.currencychallenge.ui.viewmodel.CurrencyViewModel
import com.example.currencychange.ui.theme.CurrencyChangeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyChangeTheme {
                CurrencyConverterScreen(CurrencyViewModel())
            }
        }
    }
}

