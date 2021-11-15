package com.example.testinghardvare

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.paintexample.view.SpeedView

class LigtActivity : Activity(), SensorEventListener {
    var light: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val twlight = findViewById<TextView>(R.id.tv)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light)
        val sensormanager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        light = sensormanager.getDefaultSensor(Sensor.TYPE_LIGHT) ?: null
        light?.also{sensormanager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)} ?:
        run { twlight.text = "Not find sensor" }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
    override fun onSensorChanged(event: SensorEvent?) {
        val view = findViewById<SpeedView>(R.id.Speedview)
        val twlight = findViewById<TextView>(R.id.tv)
        event?.let {
            view.setValueAnimated(it.values[0].toInt())
            twlight.text = it.values[0].toString()
        }
    }
}