package com.arhamsoft.online.transferimunityclone.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.arhamsoft.online.transferimunityclone.databinding.ActivityForgotPasswordBinding
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.ApiCall
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.URLs
import com.arhamsoft.online.transferimunityclone.models.forgetModel.ForgetPasswordModel
import com.arhamsoft.online.transferimunityclone.utils.functionsCommon.CommonFunctions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setIntents()
    }

    private fun setIntents() {
        setOnclickListeners()
    }

    private fun setOnclickListeners() {

        binding.btnLinkSend.setOnClickListener {
            sendLinkToUserEmail()
        }

        binding.crossImage.setOnClickListener {
            onBackPressed()
        }

    }

    private fun sendLinkToUserEmail() {

        if (binding.editTxtEmailLogin.text?.isEmpty() == true) {
            binding.editTxtEmailLogin.error = "Please enter email"
        } else {

            val email = binding.editTxtEmailLogin.text.toString()
            val lang = "en"

            retrofitCalling(email, lang)

        }

    }

    private fun retrofitCalling(email: String, lang: String) {

        val thread = Thread {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLs.baseURL)
                .build()
                .create(ApiCall::class.java)

            val retrofitData = retrofitBuilder.forgotPassword(
                email, lang
            )

            retrofitData.enqueue(
                object : Callback<ForgetPasswordModel> {
                    override fun onResponse(
                        call: Call<ForgetPasswordModel>,
                        response: Response<ForgetPasswordModel>,
                    ) {

                        val forgotModel = response.body()
                        if (forgotModel?.status == 1) {
                            Log.e("Response:", response.body().toString())
//                            val i = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
//                            startActivity(i)
                            CommonFunctions.showDialog(
                                this@ForgotPasswordActivity,
                                "Thank you for registration..",
                                forgotModel.message.toString(),
                                2
                            )
                        } else {
                            Log.e("Response:", response.body().toString())
                            if (forgotModel != null) {
                                forgotModel.message?.let {
                                    CommonFunctions.showDialog(
                                        this@ForgotPasswordActivity,
                                        "Ops..!!",
                                        it,
                                        1
                                    )
                                }
                            }
                        }


                    }

                    override fun onFailure(call: Call<ForgetPasswordModel>, t: Throwable) {
                        CommonFunctions.showDialog(
                            this@ForgotPasswordActivity,
                            "Ops..!!",
                            t.message!!,
                            1
                        )
//                        Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                        Log.e("Error: ", t.toString())
                    }

                },
            )

        }

        thread.start()
        thread.join()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}