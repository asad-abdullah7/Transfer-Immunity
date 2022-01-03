package com.arhamsoft.online.transferimunityclone.models.loginModel

import com.google.gson.annotations.SerializedName

data class LoginCountry (
    val id: Long,
    val code: String,
    val name: String,
    val vat: Long,

    @SerializedName("apply_default_vat")
    val applyDefaultVat: Long,

    val status: Long,

    @SerializedName("deleted_at")
    val deletedAt: Any? = null,

    @SerializedName("created_at")
    val createdAt: Any? = null,

    @SerializedName("updated_at")
    val updatedAt: String
)