package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource


var mapView: MapView? = null

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)
        mapView = findViewById(R.id.mapView)
        val initialCameraOptions = CameraOptions.Builder()
            .center(
                Point.fromLngLat(
                    47.6062,122.3321
                )
            )
            .zoom(15.5)
            .build()

        mapView?.getMapboxMap()?.setCamera(initialCameraOptions)
        mapView?.getMapboxMap()?.getStyle{
            style -> geoJsonSource("transit_stops") {
                "asset://Transit_Stops_for_King_County_Metro___transitstop_point.geojson"}
            circleLayer("transit_layer", "transit_stops"){
            }
        }






    }
}