package com.arhamsoft.online.transferimunityclone.models.loginModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields
import com.google.gson.annotations.SerializedName

@Entity(tableName = StaticFields.UserTable)
data class LoginData (

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val username: String,
    val email: String,

    @SerializedName("access_token")
    var accessToken: String,

    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any? = null,

    val timezone: String,
    val language: String,

    @SerializedName("is_expired")
    val isExpired: Long,

    @SerializedName("expired_package_disclaimer")
    val expiredPackageDisclaimer: Long,

    @SerializedName("payment_method")
    val paymentMethod: Long,

    @SerializedName("package_id")
    val packageID: Long,

    @SerializedName("last_quota_revised")
    val lastQuotaRevised: Any? = null,

    @SerializedName("on_hold_package_id")
    val onHoldPackageID: Long,

    @SerializedName("prev_package_subscription_id")
    val prevPackageSubscriptionID: Long,

    @SerializedName("package_subscription_id")
    val packageSubscriptionID: Long,

    @SerializedName("package_recurring_flag")
    val packageRecurringFlag: Long,

    @SerializedName("payment_id")
    val paymentID: Long,

    @SerializedName("mollie_customer_id")
    val mollieCustomerID: String,

    @SerializedName("profile_image")
    val profileImage: String,

    @SerializedName("last_login")
    val lastLogin: Long,

    @SerializedName("login_location")
    val loginLocation: Any? = null,

    val street: String,
    val city: String,
    val postcode: String,

    @SerializedName("country_id")
    val countryID: Long,

    @SerializedName("ip_address")
    val ipAddress: String,

    @SerializedName("on_trial")
    val onTrial: Long,

    @SerializedName("total_allocated_space")
    val totalAllocatedSpace: Double,

    @SerializedName("remaining_allocated_space")
    val remainingAllocatedSpace: Double,

    @SerializedName("max_file_size")
    val maxFileSize: Double,

    @SerializedName("switch_to_paid_package")
    val switchToPaidPackage: Long,

    @SerializedName("package_updated_by_admin")
    val packageUpdatedByAdmin: Long,

    @SerializedName("unpaid_package_email_by_admin")
    val unpaidPackageEmailByAdmin: Long,

    @SerializedName("device_token")
    val deviceToken: Any? = null,

    @SerializedName("device_id")
    val deviceID: Any? = null,

    @SerializedName("otp_auth_status")
    val otpAuthStatus: Long,

    @SerializedName("otp_auth_secret_key")
    val otpAuthSecretKey: Any? = null,

    @SerializedName("otp_auth_qr_image")
    val otpAuthQrImage: Any? = null,

    val voucher: Any? = null,

    @SerializedName("is_voucher_redeemed")
    val isVoucherRedeemed: Long,

    @SerializedName("is_secondary_accounts_created")
    val isSecondaryAccountsCreated: Long,

    @SerializedName("temp_user_file")
    val tempUserFile: Any? = null,

    @SerializedName("temp_zip_file")
    val tempZipFile: Any? = null,

    @SerializedName("dashboard_table_view")
    val dashboardTableView: Long,

    val platform: Long,
    val status: Long,

    @SerializedName("last_active_at")
    val lastActiveAt: String,

    @SerializedName("disabled_at")
    val disabledAt: Any? = null,

    @SerializedName("deleted_at")
    val deletedAt: Any? = null,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("profile_image_path")
    val profileImagePath: String,

    @SerializedName("hash_id")
    val hashID: String,

    @SerializedName("country_name")
    val countryName: String,

    @SerializedName("vat_rate")
    val vatRate: Long,

    val country: LoginCountry
)