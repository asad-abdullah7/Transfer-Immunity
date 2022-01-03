package com.arhamsoft.online.transferimunityclone.models.registerModel

import com.google.gson.annotations.SerializedName


data class RegisterModel(
    @SerializedName("data")
    val data: RegisterData,
    @SerializedName("status")
    val status: Long,
    @SerializedName("message")
    val message: Any?
) {

}

