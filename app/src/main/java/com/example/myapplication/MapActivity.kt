package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*


var mapView: MapView? = null
var p1lat = 0.0
var p1long = 0.0
var p2lat = 0.0
var p2long = 0.0





class MapActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)
        mapView = findViewById(R.id.mapView)

        if (intent.hasExtra("p1latMap")) {
            p1lat = intent.getDoubleExtra("p1latMap", 0.0)
            p1long = intent.getDoubleExtra("p1longMap", 0.0)
            p2lat = intent.getDoubleExtra("p2latMap", 0.0)
            p2long = intent.getDoubleExtra("p2longMap", 0.0)

            Log.d("Coords", "p1: " + p1long + " " + p1lat + " " + p2long + " " + p2lat)
        }




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
            style(styleUri = Style.MAPBOX_STREETS) {
                +geoJsonSource(id = "transitStops") {
                    url("asset://Transit_Stops_for_King_County_Metro___transitstop_point.geojson")
                    cluster(true)
                }
                +circleLayer(layerId = "transitCircles", sourceId = "transitStops") {
                    circleRadius(7.0)
                    circleColor(Color.BLUE)
                    circleOpacity(0.5)
                    circleStrokeColor(Color.WHITE)
                }

                +geoJsonSource(id = "sidewalkConditions") {
                    url("asset://cib3-nanr.geojson")
                    cluster(false)
                }
                +lineLayer("linelayer", "sidewalkConditions") {
                    lineCap(LineCap.ROUND)
                    lineJoin(LineJoin.ROUND)
                    lineOpacity(0.7)
                    lineWidth(8.0)
                    lineColor("#880808")
                }
            },
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                    addAnnotationToMap(Point.fromLngLat(p1long, p1lat))
                    addAnnotationToMap(Point.fromLngLat(p2long, p2lat))

                    addSideWalkLayer()
                    // Map is set up and the style has loaded. Now you can add data or make other map adjustments.
                }
            }
        )


        val annotationApi = mapView?.annotations
        val pointAnnotationManager = annotationApi?.createPointAnnotationManager()
        // Set options for the resulting symbol layer.
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            // Define a geographic coordinate.
            .withPoint(Point.fromLngLat(p1lat, p1long))
            // Specify the bitmap you assigned to the point annotation
            // The bitmap will be added to map style automatically.
            .withIconImage("green_marker.png")

        pointAnnotationManager?.create(pointAnnotationOptions)




    }



    private fun addSideWalkLayer()
    {

    }


    private fun addAnnotationToMap(p: Point) {
// Create an instance of the Annotation API and get the PointAnnotationManager.
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            this@MapActivity,
            R.drawable.red_marker
        )?.let {
            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)
            // Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            // Define a geographic coordinate.
                .withPoint(p)
                // Specify the bitmap you assigned to the point annotation
                // The bitmap will be added to map style automatically.
                .withIconImage(it)
// Add the resulting pointAnnotation to the map.
            pointAnnotationManager?.create(pointAnnotationOptions)
        }
    }
    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
// copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
    private fun addRelevant() = runBlocking {

    }

    private fun addTransitStopAnnotations() {

    }
}