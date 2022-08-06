package com.github.theruslanusmanov.dimmedclock

import retrofit2.Call
import retrofit2.http.GET

interface WeatherService {
    @GET("forecast?latitude=54.42&longitude=53.79&hourly=temperature_2m&current_weather=true")
    fun loadTemperature(): Call<Any>
}