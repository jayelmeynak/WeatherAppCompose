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
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.compose.ui.data.WeatherModel
import com.example.compose.ui.screens.MainCard
import com.example.compose.ui.screens.TabLayout
import org.json.JSONObject

const val API_KEY = "80f2a708a2fb49d7a04192911241503"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val list = remember{
                mutableStateOf(listOf<WeatherModel>())
            }
            getData("Moscow", this@MainActivity, list)

            Image(
                painter = painterResource(id = R.drawable.weather_image),
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.8f),
                contentScale = ContentScale.FillBounds
            )
            Column {
                MainCard()
                TabLayout(list.value)
            }
        }
    }
}

private fun getData(city: String, context: Context, daysList: MutableState<List<WeatherModel>>) {
    val url =
        "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$city&days=7&aqi=no&alerts=no&lang=ru"
    val queue = Volley.newRequestQueue(context)
    val request = StringRequest(
        Request.Method.GET, url, { response ->
            daysList.value = getWeatherByDays(response)
        }, {
            Log.d("MyLog", "VolleyError: $it")
        }
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
        currentTemp = mainObject.getJSONObject("current").getString("temp_c").toDouble().toInt().toString(),
    )
    return list
}

private fun String.changeStringCoding(): String{
    return String(this.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
}

