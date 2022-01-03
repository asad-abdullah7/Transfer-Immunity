package com.arhamsoft.online.transferimunityclone.utils.staticFields

import com.arhamsoft.online.transferimunityclone.models.attachmentModel.FileAttachmentModel
import com.arhamsoft.online.transferimunityclone.models.loginModel.LoginData

object StaticFields {

    var countryId = 0
    var countryName = ""
    const val DB_Name = "TransferImmunity"
    const val UserTable = "Users"
    const val FileTable = "FileModel"
    lateinit var loginData: LoginData
    var docList: ArrayList<FileAttachmentModel> = ArrayList()
}