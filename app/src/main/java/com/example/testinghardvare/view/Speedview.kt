package com.example.paintexample.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.RequiresApi
import com.example.testinghardvare.R

class SpeedView : View {


    var temp = true
    var maxVal = 200
    var runVal = 70
    val scale = 0.9f
    val longScale = 0.9f
    val textPadding = 0.85f
    var text = "LUX"
    var markRange = 20
    var color = 0xff3F51B5
    var textColor = 0xff3F51B5
    lateinit var typeface: Typeface
    constructor(context: Context?, attrs: AttributeSet?) : super (context,attrs)
    {
        init(context, attrs)
    }// конструктор

    fun init (context: Context?, attrs: AttributeSet?)
    {
        attrs?.run {
            val a = context?.obtainStyledAttributes(attrs, R.styleable.SpeedView)
            val chars = a?.getText(R.styleable.SpeedView_android_text)
            chars?.run {
                text = chars.toString()
            }
            a?.let{
                maxVal = it.getInt(R.styleable.SpeedView_maxValue, 200)
                runVal = it.getInt(R.styleable.SpeedView_value, 70)
                markRange = it.getInt(R.styleable.SpeedView_markRange, 20)
                color = it.getColor(R.styleable.SpeedView_color, 0xff3F51B5.toInt()).toLong()
                textColor = it.getColor(R.styleable.SpeedView_textColor, 0xff3F51B5.toInt()).toLong()
                chars?.run {
                    val typeface = Typeface.createFromAsset(context.assets, chars.toString())
                    paint.setTypeface(typeface)
                }
                a.recycle()
            }
        }
    }



    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        var widthV = width.toFloat()
        var heightV = height.toFloat()
        val aspect = widthV/heightV// текущее соотношение
        val normalaspect = 2f/1f// соотношение которое должно быть по замыслу
        if ( aspect > normalaspect) widthV = normalaspect * heightV
        else if (width/height < normalaspect) heightV = widthV/normalaspect
        //задали размер всегда 2/1 L/H
        canvas.apply {
            scale(.5f * widthV, -1f * heightV)// сдвиг оси в центр по длине и на низ экрана по высоте
            translate(1f, -1f)// задание размерности оси
        }
        paint.setColor(0x40ffffff)// цвет для рисования(белый полупрозрачный)
        paint.style = Paint.Style.FILL// стиль изображения - заливкаpaint.

        canvas.drawCircle(0f, 0f, 1f, paint)
        paint.setColor(0x20000000)
        canvas.drawCircle(0f,0f, 0.8f, paint)
        // нарисовали две окружности получилось полукольцо

        paint.color = color.toInt()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.005f
        val step = Math.PI / maxVal // помним, что Пи = 1/2 окружности!!!
        for (i in 0..maxVal)
        {
            var x2: Float
            var y2: Float
            var x1 = (Math.cos(Math.PI - step * i)).toFloat()
            var y1 = (Math.sin(Math.PI - step * i)).toFloat()
            if (i%markRange == 0) {
                x2 = x1 * scale * longScale
                y2 = y1 * scale * longScale
            }
            else {
                x2 = x1 * scale
                y2 = y1 * scale
            }

            canvas.drawLine(x1, y1, x2, y2, paint)
        }
        // нарисовали деления







        canvas.save()
        canvas.rotate((90 - (180 * runVal/maxVal)).toFloat())// крутим холст
        paint.color = 0xffff8899.toInt()
        paint.strokeWidth = 0.011f
        canvas.apply{
            drawLine(0.005f, 0f, 0.00f, 1f, paint)
            drawLine(-0.005f, 0f, 0.00f, 1f, paint)
            paint.style = Paint.Style.FILL
            paint.color = 0xff3F51B5.toInt()
            drawCircle(0f, 0f, 0.05f, paint)
        }// рисуем указатель
        canvas.restore()
        canvas.restore()


        canvas.save()

        paint.setTypeface(Typeface.DEFAULT)
        paint.textSize = (height/10).toFloat()
        paint.setColor(textColor.toInt())
        paint.style = Paint.Style.FILL
        canvas.translate((widthV/2), 0f)


        val factor = height * scale * longScale * textPadding
        for (i in 0..maxVal step markRange)
        {
            var x = Math.cos(Math.PI - step * i).toFloat() * factor
            var y = Math.sin(Math.PI - step * i).toFloat() * factor
            var text = i.toString()
            var textLen = Math.round(paint.measureText(text))
            canvas.drawText(text, x - textLen/2, height - y, paint)
        }// рисуем цифры вокруг циферблата
        canvas.drawText(text, -paint.measureText(text)/2, height - height * 0.15f, paint)
        canvas.restore()
    }


    fun setValue(value: Int)
    {
        var value_s = Math.min(value, maxVal)
        runVal = value_s
        invalidate()
    }    //данная функция обязательна для объекта ObjectAnimator, название функции должно быть set + "Name" передаваемое аниматору



    var objectAnimator: ObjectAnimator? = null
    public fun setValueAnimated (value: Int)
    {
        //if (temp){
        //   temp = false
        objectAnimator?.cancel()
        objectAnimator = ObjectAnimator.ofInt(this, "Value", runVal, value).apply {
            setDuration(100L + Math.abs(runVal - value) * 5)// длина анимации
            interpolator = DecelerateInterpolator()
            /* addListener(
                     object : AnimatorListenerAdapter() {
                         override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                             //super.onAnimationEnd(animation, isReverse)
                             temp = true
                             objectAnimator?.removeListener(this)
                         }
                     },
             )// этот кусок кода создан затем, чтобы запретить изменения пока не закончена анимация
             */
            start()}
        //}
        /*
        это достаточно интересный объект, которому передаются следующие параметры:
        объект анимации, в данном случае - this(SpeedView)
        строку для поиска функции установки значения по принципу "set" + "name", где name - Value, т.е. setValue
        начальное значения анимации
        конечное значение анимации
        */

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var heigh = MeasureSpec.getSize(heightMeasureSpec)
        val aspect = width/heigh
        val normalAspect = 2f/1f
        if(aspect > normalAspect)
        {
            if(widthMode != MeasureSpec.EXACTLY) width = ((normalAspect * heigh).toInt())
        }
        else if (aspect < normalAspect)
        {
            if(heightMode != MeasureSpec.EXACTLY) heigh = Math.round(width/normalAspect)
        }
        setMeasuredDimension(width, heigh)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action?: return true)
        {
            ACTION_DOWN -> {
                var newValue = getTouchValue(event.getX(), event.getY())
                setValueAnimated(newValue)
            }
            ACTION_UP -> return true
        }
        return super.onTouchEvent  (event)

    }

    fun getTouchValue(x: Float, y: Float) : Int {
        if (!(x == 0f || y == 0f))
        {
            val startX = width/2
            val startY = height
            val dirX = startX - x
            val dirY = startY - y
            val data = (Math.acos(dirX/Math.sqrt((dirX*dirX + dirY*dirY).toDouble())))
            return (Math.round(maxVal * data/Math.PI)).toInt()
        }
        else {return runVal}
    }// производим перевод из системы окна в систему приложения


}




