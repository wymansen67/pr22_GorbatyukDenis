package com.example.pr22_gorbatyukdenis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var WeatherTemp: MaterialTextView
    lateinit var WeatherPressure: MaterialTextView
    lateinit var WeatherWind: MaterialTextView
    lateinit var CityEditText: TextInputEditText
    lateinit var GetWeatherButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WeatherTemp = findViewById(R.id.temp)
        WeatherPressure = findViewById(R.id.pressure)
        WeatherWind = findViewById(R.id.wind)
        CityEditText = findViewById(R.id.appCompatEditText)
        GetWeatherButton = findViewById(R.id.getWeatherButton)
        GetWeatherButton.setOnClickListener {
                getWeather(CityEditText.text.toString())
        }
    }

    fun getWeather( city:String ){
        if(city.toString().isNotEmpty()){
            var api_key:String = "f025a9ebbf84a921fd27c5d84312f07a"
            var url = "https://api.openweathermap.org/data/2.5/weather?q=" + city.toString() + "&appid=" + api_key + "&units=metric&lang=en"

            val queue = Volley.newRequestQueue(this)
            val stringRequest= StringRequest(
                Request.Method.GET,url,{
                        response->
                    val jsonObject  = JSONObject(response)
                    val main = jsonObject.getJSONObject("main")
                    val temp = main.getString("temp").toString()
                    val pressure = main.getString("pressure").toString()
                    val wind = jsonObject.getJSONObject("wind")

                    Log.d("MyLog","Response:${temp}")
                    WeatherTemp.text = "Temp: "+ temp + "°С"
                    WeatherPressure.text = "\nPressure: "+ pressure
                    WeatherWind.text = "\nWind: "+wind.getString("speed").toString()
                },
                {
                    Log.d("MyLog","Volley error: $it")
                }
            )
            queue.add(stringRequest)
        }
        else{
            var snackBar = Snackbar.make(findViewById(android.R.id.content),"Invalid input", Snackbar.LENGTH_LONG).show()
        }
    }
}