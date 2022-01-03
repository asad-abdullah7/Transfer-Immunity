package com.arhamsoft.online.transferimunityclone.roomDataBase.DAOs

import androidx.room.*
import com.arhamsoft.online.transferimunityclone.models.fileModel.FileModelRoom
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields

@Dao
interface FileDAO {

    @Query("SELECT * FROM ${StaticFields.FileTable}")
    fun getFiles(): FileModelRoom?

    @Query("SELECT * FROM ${StaticFields.FileTable}")
    fun getAllFiles(): List<FileModelRoom>

    @Insert
    fun insertFile(fileModelRoom: FileModelRoom)

    @Update
    fun updateFile(fileModelRoom: FileModelRoom)

    @Delete
    fun deleteFile(fileModelRoom: FileModelRoom)

}