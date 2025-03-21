package com.example.climaapi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climaapi.models.Daily
import com.example.climaapi.models.Location
import com.example.climaapi.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _listaTemperaturas = MutableStateFlow<List<Daily>>(emptyList())
    val listaTemperaturas = _listaTemperaturas.asStateFlow()

    private var _ubicacion = MutableStateFlow(Location())
    val ubicacion = _ubicacion.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.retrofit.obtenerTemperaturas()

            _listaTemperaturas.value= response.body()?.timelines?.daily ?: emptyList()

            _ubicacion.value = response.body()?.location ?: Location()

        }
    }

}