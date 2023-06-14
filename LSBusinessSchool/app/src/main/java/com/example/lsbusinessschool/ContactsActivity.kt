package com.example.lsbusinessschool

import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.lsbusinessschool.databinding.ActivityContactsBinding
import com.example.lsbusinessschool.databinding.ActivityWeatherBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class ContactsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityContactsBinding

    companion object {
        private const val DEFAULT_ZOOM = 15f
        private const val ADDRESS_QUERY = "R. de Ciríaco Cardoso 186, 4150-212 Porto"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnGoBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        // Obtem a localização a partir do endereço
        val geocoder = Geocoder(this)
        try {
            val addressList = geocoder.getFromLocationName(ADDRESS_QUERY, 1)
            if (addressList != null) {
                if (addressList.isNotEmpty()) {
                    val address = addressList[0]
                    val latLng = LatLng(address.latitude, address.longitude)

                    mMap.addMarker(MarkerOptions().position(latLng).title(ADDRESS_QUERY))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM))
                }
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Erro ao obter a localização", Toast.LENGTH_SHORT).show()
        }



        //// LIGAÇÕES A OUTROS SCREENS

        /*binding.btnGoBack.setOnClickListener{
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }*/
    }
}