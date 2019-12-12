package com.jlapps.ssbu.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class API constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: API? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: API(
                    context
                ).also {
                    INSTANCE = it
                }
            }
    }
    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }


    interface OnResponseListener {
        fun onStringResponseReceived(response: String)
        fun onJsonResponseReceived(json: JSONObject)
        fun onErrorReceived(errorMessage: String)
    }
}