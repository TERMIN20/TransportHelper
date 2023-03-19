package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class RouteActivity : AppCompatActivity() {

    val locationStack = Stack<SavedLocation>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        val extras = intent.extras
        val addLocationButton = findViewById<FloatingActionButton>(R.id.addLocation)
        val fromSpinner = findViewById<Spinner>(R.id.spinner)
        val toSpinner = findViewById<Spinner>(R.id.spinner)


        //Get Location Objects and Add To Stack
        if (extras != null) {
            val myObject = intent.getSerializableExtra("loc") as? SavedLocation
            Log.d("location_tag", myObject?.id.toString())
            if (myObject != null) {
                addLocationStack(myObject)
                Log.d("locStack", locationStack.peek().toString())
            }
        }

        //
        var tempStack = locationStack
        var locationArr = Array<SavedLocation>(locationStack.size){i -> tempStack.pop()}

        //Populate Spinners with Location Objects
        if(fromSpinner!= null){


            Log.d("locArr", locationArr.toString())

            val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, locationArr)
            fromSpinner.adapter = adapter
        }

        addLocationButton.setOnClickListener {
            val intent = Intent(this, AddLocationActivity::class.java)
            startActivity(intent)
        }
    }

    fun addLocationStack(s: SavedLocation){
        locationStack.add(s)
    }


}