package com.arhamsoft.online.transferimunityclone.roomDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arhamsoft.online.transferimunityclone.models.fileModel.FileModelRoom
import com.arhamsoft.online.transferimunityclone.models.loginModel.LoginData
import com.arhamsoft.online.transferimunityclone.roomDataBase.DAOs.FileDAO
import com.arhamsoft.online.transferimunityclone.roomDataBase.DAOs.UserDAO
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields


@TypeConverters(Converter::class)
@Database(entities = [LoginData::class, FileModelRoom::class], version = 3)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDAO(): UserDAO?
    abstract fun fileDAO(): FileDAO?


    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getAppDB(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context, AppDataBase::class.java, StaticFields.DB_Name)
                        .build()
            }
            return INSTANCE
        }
    }
}