package com.arhamsoft.online.transferimunityclone.roomDataBase.DAOs

import androidx.room.*
import com.arhamsoft.online.transferimunityclone.models.loginModel.LoginData
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields

@Dao
interface UserDAO {

    @Query("SELECT * FROM ${StaticFields.UserTable}")
    fun getUser() : LoginData

    @Insert
    fun insertUser(user: LoginData)

    @Update
    fun updateUser(user: LoginData)

    @Delete
    fun deleteUser(user: LoginData)
}