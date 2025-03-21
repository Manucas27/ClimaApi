package com.example.currencychallenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencychallenge.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {
    private val _exchangeRates = MutableStateFlow<Map<String, Double>>(emptyMap())
    val exchangeRates: StateFlow<Map<String, Double>> = _exchangeRates

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchExchangeRates("USD")
    }

    fun fetchExchangeRates(baseCurrency: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getExchangeRates(baseCurrency.uppercase())

                println("Respuesta API: $response") // Ver en Logcat

                if (response.conversion_rates != null && response.conversion_rates.isNotEmpty()) {
                    _exchangeRates.value = response.conversion_rates
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "⚠️ No se recibieron tasas de la API."
                }
            } catch (e: Exception) {
                _errorMessage.value = "⚠️ Error al obtener tasas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

}
