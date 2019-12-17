package com.jlapps.ssbu.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jlapps.ssbu.model.Character
import com.jlapps.ssbu.model.CharacterList
import org.json.JSONArray

object Characters {
    private const val TAG = "API - Chars"

    fun getChars(ctx: Context,updated:MutableLiveData<Boolean>){

//        if(!::url.isInitialized)
        var url = "https://3032dmjb53.execute-api.us-east-1.amazonaws.com/dev/characters"

        API.getInstance(ctx).addToRequestQueue(object : StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                val gson = Gson()

                Log.e(TAG,"obj ")

                CharacterList.characters = gson.fromJson(response, object : TypeToken<List<Character>>() {}.type)

                updated.value = true
            },
            Response.ErrorListener {
                updated.value = false
            }
        ){})
    }
}