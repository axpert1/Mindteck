package com.app.mindteck.utils

import android.content.Context
import android.util.Log
import android.widget.ImageView

import com.app.mindteck.remote.Request
import com.app.rfid.R
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

fun Context.string(resId: Int): String = resources.getString(resId)

fun Request.asJsonObject(): JsonObject {
    return JsonParser().parse(Gson().toJson(this)).asJsonObject
}

fun String.log() {
    Log.d("RFID", this)
}

inline fun <reified T> T?.convertToResponse(): T {
    //val jsonObject = JSONObject(this as LinkedTreeMap<String, Any>)
    return Gson().fromJson(Gson().toJson(this), T::class.java)
}

 fun <T> T.treeMapListToArrayList(): ArrayList<T> {
     val gson = Gson()
     val groupListType = object : TypeToken<ArrayList<T>>() {}.type
    return gson.fromJson<ArrayList<T>>(gson.toJson(this), groupListType)
}

fun ImageView.loadImageFromUrl(url:String){
    if (url.isNotEmpty()) {
        Glide.with(this).load(url).placeholder(R.mipmap.ic_launcher).into(this)
    }
}