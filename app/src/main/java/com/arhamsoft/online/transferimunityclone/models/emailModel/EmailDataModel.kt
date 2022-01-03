package com.arhamsoft.online.transferimunityclone.models.emailModel

data class EmailDataModel (
    val id: Long,
    val from: String,
    val to: String,
    val subject: String,
    val date: String,
    val files: List<AttachedFileModel>
)