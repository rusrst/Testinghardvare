package com.example.testinghardvare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val light = findViewById<Button>(R.id.light)
        light.setOnClickListener (this :: onclick_light)
        val display = findViewById<Button>(R.id.display2)
        display.setOnClickListener(this::onclick_display)
        val sensor = findViewById<Button>(R.id.sensor)
        sensor.setOnClickListener(this::onclick_sensor)
    }
     fun onclick_light (view: View?) {
        val lightIntent = Intent (this, LigtActivity::class.java)
         startActivity(lightIntent)
    }
    fun onclick_display (view: View?) {
        val displayIntent = Intent (this, DisplayActivity::class.java)
        startActivity(displayIntent)
    }
    fun onclick_sensor(view: View?)
    {
        val sensorIntent = Intent(this, SensorActivity::class.java)
        startActivity(sensorIntent)
    }
}