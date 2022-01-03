package com.arhamsoft.online.transferimunityclone.models.emailModel

import com.google.gson.annotations.SerializedName

data class AttachedFileModel (
    val name: String,
    val type: String,
    val size: String,

    @SerializedName("short_link")
    val shortLink: String,

    val date: String
)