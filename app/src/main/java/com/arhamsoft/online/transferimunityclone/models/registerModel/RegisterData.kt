package com.arhamsoft.online.transferimunityclone.models.registerModel

import com.google.gson.annotations.SerializedName

data class RegisterData (
    val name: String,
    val username: String,
    val email: String,

    @SerializedName( "country_id")
    val countryID: String,

    val language: String,
    val timezone: String,

    @SerializedName( "last_login")
    val lastLogin: Long,

    @SerializedName( "on_hold_package_id")
    val onHoldPackageID: Any? = null,

    val platform: Long,
    val status: Long,

    @SerializedName( "updated_at")
    val updatedAt: String,

    @SerializedName( "created_at")
    val createdAt: String,

    val id: Long,

    @SerializedName( "total_allocated_space")
    val totalAllocatedSpace: Double,

    @SerializedName( "remaining_allocated_space")
    val remainingAllocatedSpace: Double,

    @SerializedName( "max_file_size")
    val maxFileSize: Double,

    @SerializedName( "package_id")
    val packageID: Long,

    @SerializedName( "package_subscription_id")
    val packageSubscriptionID: Long,

    @SerializedName( "on_trial")
    val onTrial: Long,

    @SerializedName( "package_recurring_flag")
    val packageRecurringFlag: Long,

    @SerializedName( "profile_image_path")
    val profileImagePath: String,

    @SerializedName( "hash_id")
    val hashID: String,

    @SerializedName( "country_name")
    val countryName: String,

    @SerializedName( "vat_rate")
    val vatRate: Long,

    val country: RegisterCountry
)
