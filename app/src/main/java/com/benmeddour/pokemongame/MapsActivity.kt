package com.benmeddour.pokemongame

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.benmeddour.pokemongame.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        loadPokemon()
        Checkpermissions()
    }


    val AccessLocation = 1023 // this id for access this location in the android phone , that we confirm if the user a access to your location or not down;
    fun Checkpermissions(){
        if (Build.VERSION.SDK_INT>=23){
              if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){ /*
              this for verified the  Permissions  in android 23 or larger than*/
                  requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),AccessLocation) // this for get the permission , that do a request for access to the location in this phone
                  return
              }
        }

        getUserLocation()
    }
//; this for verified id give me a access or not
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if ( requestCode == AccessLocation){// id AccessLocation here for verified if the message of request is done in this phone or not
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){ // verified if give me a access to here location or not
                getUserLocation()
            }
            else{
                Toast.makeText(this,"Location permission is deny please give me a access to your location ",Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    // this fun is to get the location of this phone after give me a access to here loation
    fun getUserLocation(){
        Toast.makeText(this,"Location permission now ,please ",Toast.LENGTH_LONG).show()
        val myLocation =MyLocationListener()
        val servicelocation = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        servicelocation.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,myLocation) // to do update to the location after 3 min or 3 metre
        val mythread =  MyThread()
        mythread.start()


        //TODO: user access Location
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        /*val sydney = LatLng(-34.0, 151.0)
        val newyork = LatLng(40.7128, 74.0060)
        mMap.addMarker(MarkerOptions().position(sydney).
        title("pokemon location").snippet("hi i'm zip zip pokemon").//snippet for more description
        icon(BitmapDescriptorFactory.fromResource(R.drawable.images1)))
        mMap.addMarker(MarkerOptions().position(newyork).title("Marker in newyork").icon(BitmapDescriptorFactory.fromResource(R.drawable.download1)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f))*/
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newyork,15f))
    }
    var mylocation:Location ?= null // the location of the user
    // this class for create a object of location
    inner class MyLocationListener:LocationListener {
        constructor(){
            mylocation =Location("me");//name of the location
            mylocation!!.longitude = 0.0
            mylocation!!.latitude = 0.0
        }
        // when the location changed
        override fun onLocationChanged(location: Location) {
            mylocation = location

        }

    }
    var myPower:Double = 0.0
    var oldlocation:Location?=null
    inner class MyThread : Thread{
        constructor():super(){
            oldlocation =Location("oldlocation");//name of the location
            oldlocation!!.longitude = 0.0
            oldlocation!!.latitude = 0.0
        }

        override fun run() {
            while (true){
                if (oldlocation!!.distanceTo(mylocation)==0f){
                    continue
                }
                oldlocation=mylocation
                try {
                    runOnUiThread {
                        mMap!!.clear() // bach matsababch machakl fi memoire
                        val myloc = LatLng(mylocation!!.latitude, mylocation!!.longitude)
                        mMap.addMarker(
                            MarkerOptions().position(myloc).title("pokemon location")
                                .snippet("hi i'm zip zip pokemon").//snippet for more description
                            icon(BitmapDescriptorFactory.fromResource(R.drawable.download))
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 12f))
                        // show other Pokemons
                        for (i in 0..listofPokeman.size - 1) {
                            var newPokemon = listofPokeman[i]
                            if (!newPokemon.isCatch) {
                                val locPoc = LatLng(newPokemon.location!!.latitude, newPokemon.location!!.longitude)
                                mMap.addMarker(
                                    MarkerOptions().position(locPoc).title(newPokemon.name)
                                        .snippet(newPokemon.des + ", Power is : "+newPokemon.Power).//snippet for more description
                                    icon(BitmapDescriptorFactory.fromResource(newPokemon.img!!))
                                )
                                if (mylocation!!.distanceTo(newPokemon.location)<10){
                                    myPower+=newPokemon.Power!!
                                    newPokemon.isCatch=true
                                    listofPokeman[i]=newPokemon
                                    Toast.makeText(applicationContext,"You catch new pockemon, your new power is :"+myPower,
                                        Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                    Thread.sleep(1000)
                }catch (ex:Exception){}
            }
        }
    }

    var listofPokeman = ArrayList<Pokeman>()
    fun loadPokemon(){
       listofPokeman.add(Pokeman("Pikatchu","lighting fight",R.drawable.images1,95.0,35.1058, -81.0179))
        listofPokeman.add(Pokeman("Aggron","hunding fight",R.drawable.download1,75.0,35.2301, -81.1303))
        listofPokeman.add(Pokeman("Aipom","bigguer fighter",R.drawable.download3,65.0,35.3301, -81.2303))
        listofPokeman.add(Pokeman("Aerodactyl","using water for fight and Flying",R.drawable.download4,60.0,35.1301, -81.3303))
    }
}