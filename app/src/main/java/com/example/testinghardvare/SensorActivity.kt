package com.example.testinghardvare

import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.testinghardvare.util.notification
import com.example.testinghardvare.view.PaintView


private val NOTIFICATION_ID = 0


class SensorActivity : Activity() {
    lateinit var sensor : PaintView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)
        sensor = findViewById<PaintView>(R.id.sensorView)
        val but = findViewById<Button>(R.id.buttonSave)
        but.setOnClickListener(){
           val str = MediaStore.Images.Media.insertImage(contentResolver, sensor.mBitmap, null, null)
            Toast.makeText(this, str, Toast.LENGTH_LONG).show()
            sensor.savePaint = true
        }
    }

    override fun onPause() {
        super.onPause()
        when (sensor.savePaint) {
            false -> {
                val notification = notification()
                val intent = Intent(this, SensorActivity::class.java)
                notification.createChannel(getString(R.string.channal_id_save_image),
                        getString(R.string.channal_name_save_image),
                this)
                notification.sendNotification(this, getString(R.string.channal_id_save_image),
                        R.drawable.ic_stat_device_unknown, getString(R.string.notification_title),
                getString(R.string.notification_text_save_image), getString(R.string.channal_content_text_save_image),
                        NOTIFICATION_ID, intent)
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        when (sensor.savePaint){
            true -> super.onBackPressed()
            false ->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("WARNING")
                builder.setMessage("YOU DONT SAVE IMAGE")
                builder.setPositiveButton("OK", DialogInterface.OnClickListener (this::onClick))
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

    }
    fun onClick (dialog: DialogInterface, which: Int)
    {
        super.onBackPressed()
    }



}