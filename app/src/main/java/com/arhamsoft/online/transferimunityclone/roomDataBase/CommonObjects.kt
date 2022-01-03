package com.arhamsoft.online.transferimunityclone.roomDataBase

import androidx.room.TypeConverter
import com.arhamsoft.online.transferimunityclone.models.loginModel.LoginCountry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Converter {

    var gson = Gson()

    @TypeConverter
    @JvmStatic
    fun toString(data: Any?): String? {
        return data?.toString()
    }

    @TypeConverter
    @JvmStatic
    fun toAny(data: String?): Any? = data

    @TypeConverter
    fun foodItemToString(foodItem: LoginCountry): String {
        return gson.toJson(foodItem)
    }

    @TypeConverter
    fun stringToFoodItem(data: String): LoginCountry {
        val listType = object : TypeToken<LoginCountry>() {
        }.type
        return gson.fromJson(data, listType)
    }


    @TypeConverter
    fun fromString(value: String?): ArrayList<String?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}