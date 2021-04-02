package com.example.reweather.view

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.reweather.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.reweather.data.apimodel.api.ApiHelper
import com.example.reweather.data.apimodel.api.RetrofitBuilder
import com.example.reweather.data.apimodel.model.WeatherBase
import com.example.reweather.util.Status
import com.example.reweather.viewmodel.MainViewModel
import com.example.reweather.viewmodel.ViewModelFactory

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    //private lateinit var adapter: MainAdapter
    private lateinit var fusedLocationClient:FusedLocationProviderClient
    private lateinit var weatherLocation:Location
    private var locationManager: LocationManager?=null
    private var  TAG="DBLOG"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


         fusedLocationClient=LocationServices.getFusedLocationProviderClient(this)

        //setupUI()
          setupViewModel()
       // setupObserver()

    }




    public fun weatherAdd(view:View){
        Log.i(TAG,"Button Press")
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),5)

        }


        fusedLocationClient.lastLocation.addOnSuccessListener {
            location: Location? ->
            var lat=location?.latitude?.toInt()
            var lon=location?.longitude?.toInt()

            textViewLat.text = lat.toString()
            textViewLon.text=lon.toString()
            if (lat != null&&lon!=null) {
                setupObserver(lat.toString(),lon.toString())
            }

        }


        /*fusedLocationClient.lastLocation.addOnCompleteListener(this){
            task-> if(task.isSuccessful&&task.result!=null){
            weatherLocation=task.result

            textViewLat.text=weatherLocation.latitude.toString()
            textViewLon.text=weatherLocation.longitude.toString()
        }else{
            if(task.result==null){
                Log.w(TAG,"getLastLocation:nul",task.exception)
            }
            Log.w(TAG,"getLastLocation:exception",task.exception)
        }



        }
*/

    }
    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }
    private fun setupObserver(lat:String,lon:String) {
        mainViewModel.getWeather(lat,lon).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { weather -> render(weather)}
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    private fun render(weather:WeatherBase){
textViewCity.text=weather.name
        textViewTemp.text=weather.main.temp.toString()

    }
}