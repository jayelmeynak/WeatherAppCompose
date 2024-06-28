package com.example.compose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.compose.ui.data.WeatherModel
import com.example.compose.ui.screens.MainCard
import com.example.compose.ui.screens.TabLayout
import com.example.compose.ui.theme.ComposeTheme
import org.json.JSONObject

const val API_KEY = "80f2a708a2fb49d7a04192911241503"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                val list = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val currentDay = remember {
                    mutableStateOf(
                        WeatherModel(
                            "Moscow",
                            "2024-06-28",
                            "22",
                            "Sunny",
                            "https://cdn.weatherapi.com/weather/64x64/day/116.png",
                            "24",
                            "10",
                            ""
                        )
                    )
                }
                getData("Moscow", this, list, currentDay)

                Image(
                    painter = painterResource(id = R.drawable.weather_image),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.8f),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard(currentDay, onClickSync = {
                        getData("Moscow", this@MainActivity, list, currentDay)
                    } )
                    TabLayout(list, currentDay)
                }
            }
        }
    }
}

private fun getData(
    city: String,
    context: Context,
    daysList: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
) {
    val url =
        "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$city&days=7&aqi=no&alerts=no&lang=ru"
    val queue = Volley.newRequestQueue(context)
    val request = StringRequest(
        Request.Method.GET, url, { response ->
            val list = getWeatherByDays(response)
            currentDay.value = list[0]
            daysList.value = list
            Log.d("MyLog", response)
        }, {

        }
    )

    request.retryPolicy = DefaultRetryPolicy(
        10000,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    )

    queue.add(request)
}

private fun getWeatherByDays(response: String): List<WeatherModel> {
    if (response.isEmpty()) return listOf()
    val list = ArrayList<WeatherModel>()
    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("location").getString("name")
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        list.add(
            WeatherModel(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day").getJSONObject("condition")
                    .getString("text").changeStringCoding(),
                item.getJSONObject("day").getJSONObject("condition")
                    .getString("icon"),
                item.getJSONObject("day").getString("maxtemp_c").toDouble().toInt().toString(),
                item.getJSONObject("day").getString("mintemp_c").toDouble().toInt().toString(),
                item.getJSONArray("hour").toString()
            )
        )
    }
    list[0] = list[0].copy(
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c").toDouble().toInt()
            .toString(),
    )
    return list
}

private fun String.changeStringCoding(): String {
    return String(this.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
}

