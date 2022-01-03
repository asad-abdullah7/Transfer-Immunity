package com.arhamsoft.online.transferimunityclone.models.forgetModel

import com.google.gson.annotations.SerializedName

data class ForgetPasswordModel (
    @SerializedName("status")
    val status: Int?,
    @SerializedName("message")
    val message: String?
) {
}