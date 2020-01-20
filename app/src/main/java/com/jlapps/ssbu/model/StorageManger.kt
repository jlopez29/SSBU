package com.jlapps.ssbu.model

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object StorageManger {
    lateinit var ctx:Context
    lateinit var storage:SharedPreferences
    var gson = Gson()


    fun init(ctx:Context){
        this.ctx = ctx
        storage = PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun addToFavorites(favorite:Character){
        var list = getFavorites()
        if(list != null && !list.contains(favorite.id)) {
            list.put(favorite.id,favorite)
            storage.edit().putString("favorites", gson.toJson(list)).commit()
        }
    }

    fun removeFromFavorites(favorite:Character){
        var list = getFavorites()
        if(list != null && list.containsKey(favorite.id)) {
            list.remove(favorite.id)
            storage.edit().putString("favorites", gson.toJson(list)).commit()
        }
    }

    fun getFavorites():HashMap<String,Character>{
        val type = object : TypeToken<HashMap<String, Character>>() {}.type
        return gson.fromJson<HashMap<String,Character>>(storage.getString("favorites", ""),type)
                ?: HashMap()
    }

}