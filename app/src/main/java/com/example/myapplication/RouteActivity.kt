package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONArray
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.util.*


class RouteActivity : AppCompatActivity() {

    var spinner1Edit = 0
    var spinner2Edit = 0

    var p1longBack: Double = 0.0
    var p1latBack: Double = 0.0


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)


        var p1long : Double = 0.0
        var p1lat : Double = 0.0

        val extras = intent.extras
        val addLocationButton = findViewById<FloatingActionButton>(R.id.addLocation)
        val addLocationButton2 = findViewById<FloatingActionButton>(R.id.addLocation2)
        val confirmButton = findViewById<Button>(R.id.confirmButton)

        val fromSpinner = findViewById<Spinner>(R.id.spinner)
        val toSpinner = findViewById<Spinner>(R.id.spinner2)

        val startLocationStack = Stack<SavedLocation>()
        val endLocationStack = Stack<SavedLocation>()
        val jsonPath = "com/example/myapplication/SavedLocations.json"

        var classMain = MainActivity()




        //Get Location Objects and Add To Stack

        Log.d("count", "count: " + spinner1Edit)

        //Populate Spinners with Location Objects
        if(fromSpinner!= null && intent.hasExtra("spinnerLoc")){


            Log.d("spinner", "adding to spinner 1")

            if (extras != null) {
                val myObject = intent.getSerializableExtra("loc") as? SavedLocation
                Log.d("location_tag", myObject?.id.toString())
                startLocationStack.add(myObject)

            }

            Log.d("locArr 1", "peek: " + startLocationStack.peek().toString())

            val locationArr1 = arrayOfNulls<SavedLocation>(1)

            if(!startLocationStack.empty()) {

                locationArr1[0] = startLocationStack.pop()
            }

            Log.d("permloc", "tempArr = " + locationArr1[0].toString())

            if (locationArr1[0] != null){
                p1lat = locationArr1[0]?.latitude!!
                p1long = locationArr1[0]?.longitude!!
                Log.d("permloc", "lat/long: " + p1lat + " " + p1long)
            }

            Log.d("permloc", "permLoc = " + classMain.permLocationArr1[0].toString())

        }



        if (classMain.permLocationArr1[0] != null) {
            Log.d("permloc", "permLOCATION != null")
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, classMain.permLocationArr1
            )
            fromSpinner.adapter = adapter
        }

        if(toSpinner!= null && !intent.hasExtra("spinnerLoc")){

            Log.d("spinner", "adding to spinner 2")
            if (extras != null) {
                val myObject = intent.getSerializableExtra("loc") as? SavedLocation
                Log.d("location_tag", myObject?.id.toString())
                endLocationStack.add(myObject)

            }

            val locationArr2 = arrayOfNulls<SavedLocation>(1)

            if(!endLocationStack.empty()) {
                locationArr2[0] = endLocationStack.pop()
            }

            classMain.permLocationArr2[0] = locationArr2[0]


        }

        if(classMain.permLocationArr2[0] != null) {
            Log.d("locArr 2", "msg")

            p1latBack = intent.getDoubleExtra("p1latBack", 6.9)
            p1longBack = intent.getDoubleExtra("p1longBack", 6.9)

            Log.d("permloc", " " + p1latBack + " " + p1longBack + " " + p1long + " " + p1lat)



            val adapter2 = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, classMain.permLocationArr2
            )
            toSpinner.adapter = adapter2
        }





        addLocationButton.setOnClickListener {
            val intent = Intent(this, AddLocationActivity::class.java)
            intent.putExtra("locButton", true)
            startActivity(intent)
        }

        addLocationButton2.setOnClickListener {

            val intent = Intent(this, AddLocationActivity::class.java)
            intent.putExtra("p1lat", p1lat)
            intent.putExtra("p1long", p1long)
            Log.d("Coords_pre_presend", "p1: " + p1long + " " + p1lat)
            startActivity(intent)
        }

        confirmButton.setOnClickListener {

            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("p1latMap", p1latBack)
            intent.putExtra("p1longMap", p1longBack)
            intent.putExtra("p2latMap", classMain.permLocationArr2[0]?.latitude)
            intent.putExtra("p2longMap", classMain.permLocationArr2[0]?.longitude)
            Log.d("Coords_presend", "p1: " + p1long + " " + p1lat)
            startActivity(intent)

        }

    }

//    fun addLocationStack(s: SavedLocation){
//        locationStack.add(s)
//    }


}