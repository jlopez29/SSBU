package com.jlapps.ssbu.view.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.jlapps.ssbu.R
import com.jlapps.ssbu.util.ViewUtils
import kotlin.math.*


class QuickWheel @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr){
    private var DEFAULT_SELECTION_COLOR = resources.getColor(R.color.orange_light_select_wheel,null)

    var isSelecting = false

    private val rect: RectF = RectF(0f, 0f, 0f, 0f)
    private val imgRect:RectF = RectF(0f, 0f, 0f, 0f)

    private var arcs = mutableListOf<Arc>()

    private val paint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val innerCirclePaint = Paint()
    private val selectedPaint: Paint = Paint()
    private var imgPaint = Paint()

    private lateinit var bitmap: Bitmap

    var radius = 0f
    var innerRadius = 0f
    var selectionColor = DEFAULT_SELECTION_COLOR



    private var slices: List<Color> = listOf(Color.RED,Color.ORANGE,Color.RED,Color.ORANGE,Color.RED,Color.ORANGE,Color.RED,Color.ORANGE)
    set(value) {
        if (field != value || arcs.isEmpty()) {
            field = value
            computeArcs()
        }
    }

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)

        getXmlAttributes(attrs)

        initPaint()

        computeArcs()
    }

    private fun getXmlAttributes(attrs: AttributeSet?){
        val a = context.obtainStyledAttributes(attrs, R.styleable.QuickWheel)
        try {
            selectionColor = a.getColor(R.styleable.QuickWheel_selectionColor, DEFAULT_SELECTION_COLOR)
        } finally {
            a.recycle()
        }
    }

    private fun initPaint(){
        bitmap = resources.getDrawable(R.drawable.mii,null).toBitmap()
        var shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        imgPaint = Paint()
        imgPaint.color = ContextCompat.getColor(context, R.color.orange_light_select_wheel)
        imgPaint.shader = shader

        paint.style = Paint.Style.FILL
        paint.strokeWidth = 4f
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true


        innerCirclePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        innerCirclePaint.color = ContextCompat.getColor(context, android.R.color.transparent)

        textPaint.textSize = 60f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = resources.getColor(R.color.white,null)

        selectedPaint.style = Paint.Style.FILL
        selectedPaint.strokeWidth = 4f
        selectedPaint.isAntiAlias = true
        selectedPaint.color = ContextCompat.getColor(context, R.color.orange_light_select_wheel)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
        innerRadius = rect.height()/3
    }

    override fun onDraw(canvas: Canvas) {
        arcs.forEachIndexed {_, arc ->

            if(arc.selected) {

                canvas.drawArc(rect, arc.start, arc.sweep, true, selectedPaint)

//                var left = rect.centerY() - 175f
//                var right = rect.centerY() + 175f
//
//                imgRect.set(left,left,right,right)


//                canvas.drawBitmap(bitmap,null,imgRect,Paint())
//                var medianAngle = (arc.start + (arc.sweep / 2f)) * Math.PI / 180f
//                radius = rect.centerY() - 50
//                var textX = (rect.centerX() + (radius * cos(medianAngle))).toFloat()
//                var textY = (rect.centerY() + (radius * sin(medianAngle))).toFloat()
//
//                canvas.drawText(index.toString(), textX, textY, textPaint)
            }
            else {
                paint.color = arc.color
                canvas.drawArc(rect, arc.start, arc.sweep, true, paint)
//                var medianAngle = (arc.start + (arc.sweep / 2f)) * Math.PI / 180f
//                radius = rect.centerY() - 50
//                var textX = (rect.centerX() + (radius * cos(medianAngle))).toFloat()
//                var textY = (rect.centerY() + (radius * sin(medianAngle))).toFloat()
//
//                canvas.drawText(index.toString(), textX, textY, textPaint)
            }

            canvas.drawCircle(rect.centerX(),rect.centerY(),innerRadius,innerCirclePaint)

//            imgRect.set(rect.centerY() - innerRadius,rect.centerY() - innerRadius,rect.centerY() + innerRadius,rect.centerY() + innerRadius)
//            canvas.drawBitmap(bitmap,null,imgRect,Paint())
        }

    }

    fun findAreaTouched(x:Float, y:Float):Int{

        arcs.forEachIndexed { index, arc ->


            var xCoord = x - rect.centerX()
            var yCoord = (y - rect.centerY()) * -1

            Log.e(TAG,"x ${xCoord} y ${yCoord}")
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
//                if(index != lastSelected)
//                    getSkin(character,index,object : Target {
//                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//                        }
//
//                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
//                        }
//
//                        override fun onBitmapLoaded(bmp: Bitmap?, from: Picasso.LoadedFrom?) {
//                            if(bmp != null) {
//                                bitmap = bmp
//                                invalidate()
//                            }
//                        }
//
//                    },170)

//                lastSelected = index
                return index
            }else
                arc.selected = false

//            Log.e(TAG,"\n\n*************\n\n")

        }

        return -1
    }

    private fun computeArcs() {

        val sweepSize: Float = 360f / slices.size * -1
        for(index in slices.indices){
            val startAngle = index * sweepSize
            if(index % 2 == 0)
                arcs.add(Arc(start = startAngle, sweep = sweepSize, color = resources.getColor(R.color.blue_dark_select_wheel,null),selected = false))
            else
                arcs.add(Arc(start = startAngle, sweep = sweepSize, color = resources.getColor(R.color.blue_light_select_wheel,null),selected = false))
        }

        invalidate()
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

    fun Drawable.toBitmap(): Bitmap {
        if (this is BitmapDrawable) {
            return bitmap
        }

        val width = if (bounds.isEmpty) intrinsicWidth else bounds.width()
        val height = if (bounds.isEmpty) intrinsicHeight else bounds.height()

        return Bitmap.createBitmap(width.nonZero(), height.nonZero(), Bitmap.Config.ARGB_8888).also {
            val canvas = Canvas(it)
            setBounds(0, 0, canvas.width, canvas.height)
            draw(canvas)
        }
    }

    private fun Int.nonZero() = if (this <= 0) 1 else this

    private fun toCartesianX(theta:Float,radius:Float) = radius * cos(theta)
    private fun toCartesianY(theta:Float,radius:Float) = radius * sin(theta)

    companion object{
        private val TAG = "Select Wheel"
        var lastKnownX = 0f
        var lastKnownY = 0f
        var lastSelected = -1


        fun initializeSelectWheel(context:Context,parentContainer:ViewGroup){
            var view = LayoutInflater.from(context).inflate(R.layout.custom_quick_wheel,null)
            var quickWheelContainer = view.findViewById<View>(R.id.fl_quick_wheel)
            var quickWheel = quickWheelContainer.findViewById<QuickWheel>(R.id.quick_wheel)

            parentContainer.addView(quickWheelContainer)

            parentContainer.setOnTouchListener{_,e ->
                if (e.action == MotionEvent.ACTION_DOWN) {
                    lastKnownX = e.x
                    lastKnownY = e.y
                    quickWheel.isSelecting = true
//                fadeViewSlow(requireContext(),false,iv_character_image){}
                } else if (e.action == MotionEvent.ACTION_UP) {
                    circularHideView(quickWheel)
                    quickWheel.isSelecting = false
                    quickWheel.resetSelection()

//                    if(lastSelected == -1)
//                        ViewUtils.fadeViewSlow(context, true, attachedView) {}
//                    else
//                        ViewUtils.transitionToSkinView(context, character, attachedView, lastSelected, false)
                }
                else if(e.action == MotionEvent.ACTION_MOVE) {


                    var newX = e.x - (quickWheelContainer.x)
                    var newY = e.y - (quickWheelContainer.y)
                    var area = quickWheel.findAreaTouched(newX,newY)
                    if(area != lastSelected) {
                        lastSelected = area
//                        ViewUtils.transitionToSkinView(context, character, attachedView, lastSelected, true)
//                    transitionSkinView(context(),character,iv_character_image)
                    }

                }else if(e.action == MotionEvent.ACTION_CANCEL){
//                    ViewUtils.fadeViewSlow(context, true, attachedView) {}
                }

                false
            }

            parentContainer.setOnLongClickListener {

                if(quickWheel.visibility == INVISIBLE){
                    quickWheelContainer.x = lastKnownX - quickWheelContainer.width/2
                    quickWheelContainer.y = lastKnownY - quickWheelContainer.height/2
                    circularRevealView(quickWheel)
                }
                false
            }
        }

        fun initializeSelectWheel(context:Context,parentContainer:ViewGroup,attachedView:View,collapsingLayout: ViewGroup){
            var view = LayoutInflater.from(context).inflate(R.layout.custom_quick_wheel,null)
            var quickWheelContainer = view.findViewById<View>(R.id.fl_quick_wheel)
            var quickWheel = quickWheelContainer.findViewById<QuickWheel>(R.id.quick_wheel)

            parentContainer.addView(quickWheelContainer)

            attachedView.post {

                var xOffset = attachedView.width * .5
                var yOffset = attachedView.height * .5
                var width = (attachedView.width + xOffset).toInt()
                var height = (attachedView.height + yOffset).toInt()
                quickWheel.layoutParams = FrameLayout.LayoutParams(width, height)
                quickWheel.requestLayout()

                quickWheelContainer.x = (attachedView.x - (xOffset/2)).toFloat()
                quickWheelContainer.y = (attachedView.y - (yOffset/2)).toFloat()
                quickWheelContainer.requestLayout()
            }

            attachedView.setOnTouchListener{_,e ->
                if (e.action == MotionEvent.ACTION_DOWN) {
                    lastKnownX = e.x
                    lastKnownY = e.y
                    quickWheel.isSelecting = true
//                fadeViewSlow(requireContext(),false,iv_character_image){}
                } else if (e.action == MotionEvent.ACTION_UP) {
                    circularHideView(quickWheel)
                    enableScroll(collapsingLayout)
                    quickWheel.isSelecting = false
                    quickWheel.resetSelection()

//                    if(lastSelected == -1)
//                        ViewUtils.fadeViewSlow(context, true, attachedView) {}
//                    else
//                        ViewUtils.transitionToSkinView(context, character, attachedView, lastSelected, false)
                }
                else if(e.action == MotionEvent.ACTION_MOVE) {

                    var newX = e.x
                    var newY = e.y
                    var area = quickWheel.findAreaTouched(newX,newY)
                    if(area != lastSelected) {
                        lastSelected = area
//                        ViewUtils.transitionToSkinView(context, character, attachedView, lastSelected, true)
//                    transitionSkinView(context(),character,iv_character_image)
                    }

                }else if(e.action == MotionEvent.ACTION_CANCEL){
                    ViewUtils.fadeViewSlow(context, true, attachedView) {}
                }

                false
            }

            attachedView.setOnLongClickListener {

                if(quickWheel.visibility == View.INVISIBLE){
//                quickWheelContainer.x = iv_character_image.width/2f
//                quickWheelContainer.y = iv_character_image.height/2f
                    disableScroll(collapsingLayout)
                    circularRevealView(quickWheel)
                }
                false
            }
        }

        private fun enableScroll(view:ViewGroup){
            val params = view.getLayoutParams() as AppBarLayout.LayoutParams
            params.scrollFlags = (
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                            or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                    )
            view.setLayoutParams(params)
            view.requestDisallowInterceptTouchEvent(false)
        }

        private fun disableScroll(view:ViewGroup) {
            val params = view.getLayoutParams() as AppBarLayout.LayoutParams
            params.scrollFlags = 0
            view.setLayoutParams(params)
            view.requestDisallowInterceptTouchEvent(true)
        }
    }

}