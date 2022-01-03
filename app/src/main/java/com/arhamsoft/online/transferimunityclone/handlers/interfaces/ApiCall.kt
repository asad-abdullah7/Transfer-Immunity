package com.arhamsoft.online.transferimunityclone.handlers.interfaces

import com.arhamsoft.online.transferimunityclone.models.countryModel.CountryModel
import com.arhamsoft.online.transferimunityclone.models.emailModel.EmailModel
import com.arhamsoft.online.transferimunityclone.models.emailModel.SentEmailResponseBack
import com.arhamsoft.online.transferimunityclone.models.forgetModel.ForgetPasswordModel
import com.arhamsoft.online.transferimunityclone.models.loginModel.LoginModel
import com.arhamsoft.online.transferimunityclone.models.registerModel.RegisterModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiCall {

    @FormUrlEncoded
    @POST(URLs.login)
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("platform") platform: String,
        @Field("lang") languageCode: String
    ): Call<LoginModel>

    @FormUrlEncoded
    @POST(URLs.register)
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("country_id") countryId: Int,
        @Field("platform") platform: String,
        @Field("lang") languageCode: String
    ): Call<RegisterModel>


    @FormUrlEncoded
    @POST(URLs.forgetPassword)
    fun forgotPassword(
        @Field("email") email: String,
        @Field("lang") languageCode: String
    ): Call<ForgetPasswordModel>

    @GET(URLs.countries)
    fun getCountries(): Call<CountryModel>

    @GET(URLs.fetchEmail)
    fun fetchEmails(): Call<EmailModel>


    @FormUrlEncoded
    @POST(URLs.sendEmailStore)
    fun sendEmailStore(
        @Field("to") to: String,
        @Field("from") from: String,
        @Field("subject") subject: String,
        @Field("message") message: String,
        @Field("file_ids") fileIds: String,
        @Field("platform") platform: String,
        @Field("lang") languageCode: String
    ): Call<SentEmailResponseBack>

}