package com.github.theruslanusmanov.dimmedclock.api

import retrofit2.Call
import retrofit2.http.GET

interface WeatherService {
    @GET("forecast?latitude=55.7558&longitude=37.6176&hourly=temperature_2m&current_weather=true")
    fun loadTemperature(): Call<Any>
}