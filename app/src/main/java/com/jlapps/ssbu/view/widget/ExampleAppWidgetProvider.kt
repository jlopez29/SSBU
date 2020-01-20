package com.jlapps.ssbu.view.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.jlapps.ssbu.R

class ExampleAppWidgetProvider : AppWidgetProvider() {
    var TAG = "EAWP"
    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) {
        Log.e(TAG,"On Update")
        // Perform this loop procedure for each App Widget that belongs to this provider
        appWidgetIds.forEach { appWidgetId ->

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            val views = RemoteViews(
                    context.packageName,
                    R.layout.widget_layout
            )

            var intent = Intent(context, ImageFlipperWidgetService::class.java)
            views.setRemoteAdapter(appWidgetId,R.id.avf_widget_characters, intent)
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}