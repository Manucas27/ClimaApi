package com.example.climaapi.network.response

import com.example.climaapi.models.Location
import com.example.climaapi.models.Timelines

data class TemperaturasResponse(
    val location: Location,
    val timelines: Timelines
)