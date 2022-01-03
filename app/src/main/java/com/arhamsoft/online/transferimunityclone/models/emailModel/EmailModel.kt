package com.arhamsoft.online.transferimunityclone.models.emailModel

import com.google.gson.annotations.SerializedName
import java.io.File

data class EmailModel (
    val data: List<EmailDataModel>,
    val message: String,
    val status: Long
) {
}


