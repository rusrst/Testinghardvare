package com.example.testinghardvare.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.example.testinghardvare.R
import kotlin.Exception



private const val STROKE_WIDTH = 22f

class PaintView : View{
    var savePaint = true
    var path = Path()
    var drawColor = 0xFF000000f
    var backgroundColor = 0xFFFFFFFF

    lateinit var mBitmap: Bitmap
    lateinit var mCanvas: Canvas
    val paintView = Paint().apply {
        color = drawColor.toInt()
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
    }
    val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop
    var currentX = 0f
    var currentY = 0f
    var motionEventX = 0f
    var motionEventY = 0f
    constructor(context: Context?, attrs: AttributeSet?) : super (context,attrs)
    {

    }// конструктор

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (:: mBitmap.isInitialized) mBitmap.recycle()
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        mCanvas.drawColor(backgroundColor.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmap, 0f, 0f, null)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionEventX = event.x
        motionEventY = event.y
        when (event.action)
        {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    fun touchStart(){
        //path.reset()
        path.moveTo(motionEventX, motionEventY)
        currentX = motionEventX
        currentY = motionEventY
        mCanvas.drawCircle(currentX, currentY,  2f, paintView)
        savePaint = false
        invalidate()
    }

    fun touchMove(){
        val dx = Math.abs(motionEventX - currentX)
        val dy = Math.abs(motionEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance){
            path.quadTo(currentX, currentY, motionEventX, motionEventY)
            currentX = motionEventX
            currentY = motionEventY
            mCanvas.drawPath(path, paintView)
            savePaint = false
            invalidate()
        }
    }

    fun touchUp(){
        path.reset()
    }

}