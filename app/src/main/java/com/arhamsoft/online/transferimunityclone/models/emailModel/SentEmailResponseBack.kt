package com.arhamsoft.online.transferimunityclone.models.emailModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SentEmailResponseBack {
    @SerializedName("status")
    @Expose
    var status: Int? = 0

    @SerializedName("message")
    @Expose
    var message: String? = ""
}