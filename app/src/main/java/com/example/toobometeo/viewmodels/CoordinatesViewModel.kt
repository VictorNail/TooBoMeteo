package com.example.toobometeo.viewmodels
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import com.example.toobometeo.models.Coordinates

class CoordinatesViewModel : ViewModel() {

    var text: String = "Vos Coordonnées GPS sont : "
    var coordinates: Coordinates = Coordinates(1.2345f, 6.7890f)

    fun getTextWithCoordinates(): String {
        return "$text ${coordinates?.longitude} / ${coordinates?.latitude}"
    }

}