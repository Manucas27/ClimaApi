package com.example.climaapi.network

import android.health.connect.datatypes.units.Temperature
import com.example.climaapi.network.response.TemperaturasResponse
import com.example.climaapi.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("forecast?location=${Constantes.LOCATION}&apikey=${Constantes.API_KEY}")
    suspend fun obtenerTemperaturas()
    :Response<TemperaturasResponse>
}