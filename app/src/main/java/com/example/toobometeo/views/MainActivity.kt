package com.example.toobometeo.views

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ayush.retrofitexample.RetrofitHelper
import com.ayush.retrofitexample.localisationHelper
import com.example.toobometeo.R
import com.example.toobometeo.database.CityRoomDatabase
import com.example.toobometeo.viewmodels.CoordinatesViewModel
import com.example.toobometeo.databinding.ActivityMainBinding
import com.example.toobometeo.entity.WeatherCityEntity
import com.example.toobometeo.repository.weatherCityRepository
import com.example.toobometeo.viewmodels.OpenMeteoApi
import com.example.toobometeo.viewmodels.adressGouvApi
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        val textViewCity = findViewById<TextView>(R.id.city_title)
        val textViewDegre = findViewById<TextView>(R.id.temperature)
        val textChange = findViewById<TextView>(R.id.texte)
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val weatherApi = RetrofitHelper.getInstance().create(OpenMeteoApi::class.java)
        // launching a new coroutine
        GlobalScope.launch {
            val result = weatherApi.getForecast(
                44.8378,
                0.5792,
                "temperature_2m,weathercode,windspeed_180m",
                "weathercode",
                "Europe/London"
            )
            val temperatures = result.body()?.hourly?.temperature_2m
            if (temperatures != null) {
                runOnUiThread {
                    textViewDegre.text = temperatures[hour].toString() + "°C"
                }
            }
        }

        //database
        val applicationScope = CoroutineScope(SupervisorJob())
        val database = CityRoomDatabase.getDatabase(this, applicationScope)
        val respository = weatherCityRepository(database.cityDao())
        applicationScope.launch(Dispatchers.IO) { respository.insert(WeatherCityEntity("Test")) }


        val searchBar = findViewById<EditText>(R.id.search_bar)
        val cityApi = localisationHelper.getInstance().create(adressGouvApi::class.java)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Cette méthode est appelée avant que le texte ne soit modifié
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Cette méthode est appelée lorsque le texte est modifié
            }

            override fun afterTextChanged(s: Editable?) {
                // launching a new coroutine
                GlobalScope.launch {
                    val result = cityApi.getSearch(searchBar.text.toString())
                    runOnUiThread {
                        textViewCity.text = result.body()?.features?.get(0)?.properties?.label ?: ""
                        val latitude:Double = result.body()?.features?.get(0)?.geometry?.coordinates?.get(1) ?: 44.8378
                        val longitude:Double = result.body()?.features?.get(0)?.geometry?.coordinates?.get(0) ?: 0.5792
                        GlobalScope.launch {
                            val result = weatherApi.getForecast(
                                latitude,
                                longitude,
                                "temperature_2m,weathercode,windspeed_180m",
                                "weathercode",
                                "Europe/London"
                            )
                            val temperatures = result.body()?.hourly?.temperature_2m
                            if (temperatures != null) {
                                runOnUiThread {
                                    textViewDegre.text = temperatures[hour].toString() + "°C"
                                }
                            }
                            val codes = result.body()?.hourly?.weathercode
                            //changement background
                            runOnUiThread {
                                if (linearLayout != null) {
                                    when (codes?.get(hour)?.toInt()) {
                                        0 -> linearLayout.setBackgroundResource(R.drawable.soleil)
                                        1, 2, 3 -> linearLayout.setBackgroundResource(R.drawable.eclaircie)
                                        85, 86, 71, 73, 75, 77 -> linearLayout.setBackgroundResource(R.drawable.snow)
                                        80, 81, 82 -> linearLayout.setBackgroundResource(R.drawable.pluie)
                                        45, 48 -> linearLayout.setBackgroundResource(R.drawable.fog)
                                        95, 96, 99 -> linearLayout.setBackgroundResource(R.drawable.eclair)
                                        51, 53, 55, 56, 57, 61, 63, 65, 66, 67 -> linearLayout.setBackgroundResource(R.drawable.petitepluie)
                                        else -> {
                                            linearLayout.setBackgroundResource(R.drawable.soleil)
                                        }
                                    }
                                } else {
                                    Log.d("MainActivity", "Layout not shown yet")
                                }
                            }
                            //Prochain Changement
                            var i = hour
                            var stop =true
                            codes?.forEach { code ->
                                i++
                                if(codes?.get(hour) != code && stop){
                                    var hourChange = i-hour
                                    runOnUiThread {
                                        textChange.text =
                                            "Prochain changement dans: " + hourChange.toString() +"H"
                                    }
                                    stop = false
                                }
                            }
                        }
                    }
                }
            }
        })
    }
}



