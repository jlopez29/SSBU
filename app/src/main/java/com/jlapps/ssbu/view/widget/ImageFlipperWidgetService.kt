package com.jlapps.ssbu.view.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.jlapps.ssbu.R
import com.jlapps.ssbu.model.Character
import com.jlapps.ssbu.model.StorageManger
import com.jlapps.ssbu.model.formatString
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random


class ImageFlipperWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        StorageManger.init(this.applicationContext)
        return ImageFlipperRemoteViewsFactory(this.applicationContext, intent)
    }
}

class ImageFlipperRemoteViewsFactory(
        private val context: Context,
        intent: Intent
) : RemoteViewsService.RemoteViewsFactory {
    val TAG = "Image Flipper"
    var usedImages = HashMap<String,Bitmap>()
    var characters = HashMap<String,Character>()
    override fun onCreate() {
//        Log.e(TAG,"onCreate")
        StorageManger.init(context)
        characters = StorageManger.getFavorites()
    }

    override fun getLoadingView(): RemoteViews? {
//        Log.e(TAG,"getLoadingView")
        return null
    }

    override fun getItemId(position: Int): Long {
//        Log.e(TAG,"getItemId")
        return position.toLong()
    }

    override fun onDataSetChanged() {
//        Log.e(TAG,"onDataSetChanged")
        StorageManger.init(context)
        characters = StorageManger.getFavorites()
    }

    override fun hasStableIds(): Boolean {
//        Log.e(TAG,"hasStableIds")
        return true
    }

    override fun getViewAt(position: Int): RemoteViews {
        Log.e(TAG,"getViewAt ${position}")
        StorageManger.init(context)
        characters = StorageManger.getFavorites()

        var char = characters.values.toList().get(position)

        val rv = RemoteViews(context.getPackageName(), R.layout.item_widget_character)
        var url = "https://storage.googleapis.com/ssbu-3d1bf.appspot.com/skins/${char.name.formatString()}/"+ Random.nextInt(0,8)


        if(!usedImages.containsKey(url)) {
            var image = getBitmapFromURL(url)
            rv.setImageViewBitmap(R.id.iv_widget_character, image)

            if(image != null)
                usedImages.put(url,image)
        }
        else{
            rv.setImageViewBitmap(R.id.iv_widget_character,usedImages.get(url))
        }

        return rv
    }

    override fun getCount(): Int {
        Log.e(TAG,"getCount ${characters.size}")
        return characters.size
    }

    override fun getViewTypeCount(): Int {
//        Log.e(TAG,"getViewTypeCount")
        return 1
    }

    override fun onDestroy() {
//        Log.e(TAG,"onDestroy")
    }

    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) { // Log exception
            null
        }
    }
}