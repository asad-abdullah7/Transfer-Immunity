package com.arhamsoft.online.transferimunityclone.models.fileModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields

@Entity(tableName = StaticFields.FileTable)
class FileModelRoom(

    @ColumnInfo(name = "To")
    var to: String? = "",
    @ColumnInfo(name = "From")
    var from: String? = "",
    @ColumnInfo(name = "Subject")
    var subject: String? = "",
    @ColumnInfo(name = "Message")
    var message: String? = "",
    @ColumnInfo(name = "Date")
    var date : String? = "",
    @ColumnInfo(name = "fileName")
    var fileName: ArrayList<String>? = ArrayList(),
    @ColumnInfo(name = "fileIds")
    var fileIds: ArrayList<String>? = ArrayList(),
    @ColumnInfo(name = "FilePath")
    var filePath: ArrayList<String>? = ArrayList(),
    @ColumnInfo(name = "FileLink")
    var fileLink: ArrayList<String>? = ArrayList(),
    @ColumnInfo(name = "Status")
    var status: ArrayList<String>? = ArrayList(),
    @ColumnInfo(name = "Progress")
    var progress: ArrayList<String>? = ArrayList(),
    @ColumnInfo(name = "fileSize")
    var fileSize: ArrayList<String>? = ArrayList()
){
    @PrimaryKey(autoGenerate = true)
    var ID: Int = 0
}