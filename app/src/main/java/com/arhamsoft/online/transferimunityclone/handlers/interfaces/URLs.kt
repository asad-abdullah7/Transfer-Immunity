package com.arhamsoft.online.transferimunityclone.handlers.interfaces

object URLs {
    const val baseURL = "https://www.transferimmunity.com/"
    const val register = "api/auth/register"
    const val login = "api/auth/login"
    const val logout = "api/auth/logout"
    const val forgetPassword = "api/auth/password/send-reset-link"
    const val getProfile = "api/auth/profile"
    const val updateProfile = "api/auth/update-profile"
    const val resendEmail = "api/auth/resend-verification-email"

    //
    const val packageStatus = "api/extensions/package/status"
    const val uploadFile = "api/extensions/upload/file"
    const val sendEmailStore = "api/send-emails/store"
    const val fetchEmail = "api/send-emails"
    const val countries = "api/countries"

    //
    const val packagesURL = "https://www.transferimmunity.com/upgrade-package?"
    const val dashBoardURL = "https://www.transferimmunity.com/dashboard?"
    const val termsURL = "https://www.transferimmunity.com/pages/terms-and-conditions"
    const val privacyURL = "https://www.transferimmunity.com/pages/privacy-policy"
    const val profileURL =
        "https://www.transferimmunity.com/profile?user_id=%s&direct_login=1&lang=%s"
}