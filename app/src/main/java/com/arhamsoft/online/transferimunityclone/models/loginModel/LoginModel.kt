package com.arhamsoft.online.transferimunityclone.models.loginModel

import com.google.gson.annotations.SerializedName


data class LoginModel (
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("expires_in")
    val expiresIn: Any? = null,

    val data: LoginData,
    val status: Long,
    val message: String
) {

}




