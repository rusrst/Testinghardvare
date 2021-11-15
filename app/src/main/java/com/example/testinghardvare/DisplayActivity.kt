package com.example.testinghardvare

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class DisplayActivity : Activity() {
    var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        val display = findViewById<View>(R.id.display2)
        display.setBackgroundColor(0xffffffff.toInt())
        display.setOnClickListener(this:: onclick_background)
        }
    fun onclick_background (view: View?)
    {
        counter++
        if (counter > 4) counter = 0
        when (counter)
        {
            0-> view?.setBackgroundColor(0xffffffff.toInt())
            1-> view?.setBackgroundColor(0xffff0000.toInt())
            2-> view?.setBackgroundColor(0xff00ff00.toInt())
            3-> view?.setBackgroundColor(0xff0000ff.toInt())
            4-> view?.setBackgroundColor(0xff000000.toInt())
        }
    }

}
