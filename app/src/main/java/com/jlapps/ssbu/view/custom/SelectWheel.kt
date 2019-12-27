package com.jlapps.ssbu.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.*
import androidx.core.content.ContextCompat
import com.jlapps.ssbu.R
import kotlin.math.*


class SelectWheel @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr){

    private var radius = 0f

    private var slices: MutableList<Color> = mutableListOf()
    set(value) {
        if (field != value || arcs.isEmpty()) {
            field = value
            computeArcs()
        }
    }
    var amount: Int = 0
        get() = 0
        set(value){
            field = value
            initSlices()
            computeArcs()
        }

    fun initSlices(){
        for(i in 0..amount){
            if(i % 2 == 0)
                slices.add(Color.ORANGE)
            else
                slices.add(Color.RED)
        }
    }


    private var arcs = emptyList<Arc>()
    private val paint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val selectedPaint: Paint = Paint()
    private val greyColor = ContextCompat.getColor(context, R.color.grey)


    private val colorMap = mapOf(Color.WHITE to ContextCompat.getColor(context, R.color.white),
            Color.BLUE to ContextCompat.getColor(context, R.color.blue),
            Color.BLACK to ContextCompat.getColor(context, R.color.black),
            Color.RED to ContextCompat.getColor(context, R.color.red),
            Color.ORANGE to ContextCompat.getColor(context, R.color.orange_dark),
            Color.GREEN to ContextCompat.getColor(context, android.R.color.holo_green_light))

    private fun computeArcs() {
        arcs = if (slices.isEmpty()) {
            listOf(Arc(0f, 360f, greyColor,false))
        } else {
            val sweepSize: Float = 360f / slices.size * -1
            slices.mapIndexed { index, color ->
                val startAngle = index * sweepSize
                Arc(start = startAngle, sweep = sweepSize, color = colorMap.getValue(color),selected = false)
            }
        }
        invalidate()
    }

    init {
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        textPaint.textSize = 50f
        textPaint.textAlign = Paint.Align.CENTER

        selectedPaint.style = Paint.Style.FILL
        selectedPaint.isAntiAlias = true
        selectedPaint.color = ContextCompat.getColor(context, android.R.color.holo_green_light)

        computeArcs()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        arcs.forEachIndexed {index, arc ->

            if(arc.selected) {

                canvas.drawArc(rect, arc.start, arc.sweep, true, selectedPaint)
                var medianAngle = (arc.start + (arc.sweep / 2f)) * Math.PI / 180f
                radius = rect.centerY()
                var textX = (rect.centerX() + (radius * cos(medianAngle)) / 2).toFloat()
                var textY = (rect.centerY() + (radius * sin(medianAngle)) / 2).toFloat()

                textPaint.color = resources.getColor(R.color.black,null)
                canvas.drawText(index.toString(), textX, textY, textPaint)
            }
            else {
                paint.color = arc.color
                canvas.drawArc(rect, arc.start, arc.sweep, true, paint)
                var medianAngle = (arc.start + (arc.sweep / 2f)) * Math.PI / 180f
                radius = rect.centerY()
                var textX = (rect.centerX() + (radius * cos(medianAngle)) / 2).toFloat()
                var textY = (rect.centerY() + (radius * sin(medianAngle)) / 2).toFloat()

                textPaint.color = resources.getColor(R.color.white,null)
                canvas.drawText(index.toString(), textX, textY, textPaint)
            }
        }
    }

    private val TAG = "Select Wheel"
    var isSelecting = false
    private val rect: RectF = RectF(0f, 0f, 0f, 0f)
    fun findAreaTouched(x:Float, y:Float):Int{

        arcs.forEachIndexed { index, arc ->


            var xCoord = x - rect.centerX()
            var yCoord = (y - rect.centerY()) * -1



            var angle = atan2(yCoord,xCoord)
            angle = (angle * (180/ PI)).toFloat()

            if(angle < 0)
                angle += 360


            var start = abs(arc.start)


            var end = abs(arc.start + arc.sweep)

//            Log.e(TAG,"\n\n*************${index}\n\n")
//            Log.e(TAG,"x,y ${xCoord},${yCoord}")
//            Log.e(TAG,"angle ${angle}")
//            Log.e(TAG,"start angle is ${start}")
//            Log.e(TAG,"end angle is ${end}")

            if(angle in start..end) {
//                    Log.e(TAG, "Touched sliced ${index}")
                arc.selected = true
                for( arc1 in arcs )
                    if(arc1 != arc)
                        arc1.selected = false

//                recalculateArcs()
                invalidate()
                return index
            }else
                arc.selected = false

//            Log.e(TAG,"\n\n*************\n\n")

        }

        return -1
    }

    fun recalculateArcs(){
        val sweepSize: Float = 360f / slices.size * -1
        arcs.forEachIndexed { index, arc ->
            if(arc.selected) {
                val startAngle = index * sweepSize
            }
        }
    }

    fun resetSelection(){
        for( arc in arcs )
            arc.selected = false
    }

    companion object{
        var lastKnownX = 0f
        var lastKnownY = 0f
        var lastTouchedSlice = 0
        var lastSelected = 1
    }

}

private class Arc(var start: Float, var sweep: Float, var color: Int, var selected:Boolean)

enum class Color {
    WHITE, BLUE, BLACK, RED, ORANGE, GREEN
}