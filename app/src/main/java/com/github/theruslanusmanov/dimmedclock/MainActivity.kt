package com.github.theruslanusmanov.dimmedclock

import android.os.Bundle
import android.util.Log
import android.widget.TextClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.theruslanusmanov.dimmedclock.api.WeatherService
import com.github.theruslanusmanov.dimmedclock.ui.theme.DimmedClockTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    val BASE_URL = "https://api.open-meteo.com/v1/"
    var temperature: String = "13"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl(this.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: WeatherService = retrofit.create(WeatherService::class.java)
        val tmp = service.loadTemperature()
        tmp.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                Log.d("TMP", response.toString())
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                // Log error here since request failed
            }
        })

        setContent {
            DimmedClockTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Clock()
                        Temperature(temperature)
                    }
                }
            }
        }
    }
}

@Composable
fun Clock() {
    AndroidView(
        factory = { context ->
            TextClock(context).apply {
                format12Hour?.let { this.format12Hour = "hh:mm" }
                timeZone?.let { this.timeZone = it }
                textSize.let { this.textSize = 128f }
                this.setTextColor(Color.White.hashCode())

            }
        },
        modifier = Modifier.padding(5.dp),
    )
}

@Composable
fun Temperature(temperature: String) {
    Text(
        text = "$temperature°",
        color = Color.White,
        style = MaterialTheme.typography.h2,
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DimmedClockTheme {
        Clock()
    }
}