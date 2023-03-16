package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.internal.bind.TypeAdapters.URI
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import java.net.URI
import java.net.URISyntaxException


var mapView: MapView? = null

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)
        mapView = findViewById(R.id.mapView)

        //Setting Map Camera to Seattle
        val initialCameraOptions = CameraOptions.Builder()
            .center(
                Point.fromLngLat(
                    -122.335167,47.608013
                )
            )
            .zoom(11.0)
            .build()

        //Applying change
        mapView?.getMapboxMap()?.setCamera(initialCameraOptions)


        mapView?.getMapboxMap()?.loadStyle(
            style(styleUri = Style.TRAFFIC_DAY) {
                +geoJsonSource(id = "transitStops") {
                    url("asset://Transit_Stops_for_King_County_Metro___transitstop_point.geojson")
                    cluster(true)
                }
                +circleLayer(layerId = "transitCircles", sourceId = "transitStops") {
                    circleRadius(20.0)
                    circleColor(Color.RED)
                    circleOpacity(0.3)
                    circleStrokeColor(Color.WHITE)
                }
            },
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                    // Map is set up and the style has loaded. Now you can add data or make other map adjustments.
                }
            }
        )



//        mapView?.getMapboxMap()?.getStyle{
//            style -> geoJsonSource("transit_stops") {
//                "asset://Transit_Stops_for_King_County_Metro___transitstop_point.geojson"}
//            circleLayer("transit_layer", "transit_stops"){
//                circleRadius(20.0)
//                circleColor(Color.RED)
//                circleOpacity(0.3)
//                circleStrokeColor(Color.WHITE)
//            }
//        }


    }

    private fun addTransitStopAnnotations() {

    }
}