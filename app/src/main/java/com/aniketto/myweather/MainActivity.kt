package com.aniketto.myweather

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.*
import com.android.volley.Request.Method.*
import com.android.volley.Response
import com.android.volley.Response.ErrorListener
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lat = intent.getStringExtra("lat")
        val long= intent.getStringExtra("long")

       // window.statusBarColor = Color.parseColor(getColor())
        getJsonData(lat,long)
    }
    private fun getJsonData(lat:String? , long:String?) {
        val API_key = "47e64fec8a2da405248a680976a99739"
        val queue = Volley.newRequestQueue(this)
        val url ="https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_key}"

        val jsonRequest = JsonObjectRequest(
            url,  null , { response ->
                setValues(response)
            }, ErrorListener{ }
        )
        queue.add(jsonRequest)
    }
    @SuppressLint("SetTextI18n")
    private fun setValues(response:JSONObject){
        city.text = response.getString("name")

        var lat = response.getJSONObject("coord").getString("lat")
        var long = response.getJSONObject("coord").getString("long")
        coordinates.text = "${lat} , ${long}"

        weather.text = response.getJSONArray("weather").getJSONObject(0).getString("main")

        var tempr = response.getJSONObject("main").getString("temp")
        tempr = ((((tempr).toFloat() - 273.15)).toInt()).toString()
        temp.text = "${tempr} + °C"

        var mintemp= response.getJSONObject("main").getString("temp_min")
        mintemp = ((((mintemp).toFloat() - 273.15)).toInt()).toString()
        min_temp.text = "${mintemp} + °C"

        var maxtemp= response.getJSONObject("main").getString("temp_min")
        maxtemp= ((ceil((maxtemp).toFloat() - 273.15)).toInt()).toString()
        max_temp.text = "${maxtemp} + °C"

        pressure.text = response.getJSONObject("main").getString("pressure")
        humidity.text = response.getJSONObject("main").getString("humidity")
        wind.text = response.getJSONObject("main").getString("speed")
        degree.text = "Degree:- "+ response.getJSONObject("wind").getString("deg") + "°"
    }
}

