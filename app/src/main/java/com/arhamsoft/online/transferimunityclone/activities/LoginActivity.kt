package com.arhamsoft.online.transferimunityclone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arhamsoft.online.transferimunityclone.databinding.ActivityLoginBinding
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.ApiCall
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.URLs
import com.arhamsoft.online.transferimunityclone.models.loginModel.LoginModel
import com.arhamsoft.online.transferimunityclone.roomDataBase.AppDataBase
import com.arhamsoft.online.transferimunityclone.utils.functionsCommon.CommonFunctions
import com.arhamsoft.online.transferimunityclone.utils.sharedPreferences.CustomSharedPref
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: CustomSharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = CustomSharedPref(this)
        setIntents()
    }

    private fun setIntents() {

        setOnClickListeners()

    }

    private fun setOnClickListeners() {

        binding.txtRight.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }

        binding.btnLogin.setOnClickListener {
            userLogin()
        }

        binding.txtForgotPassword.setOnClickListener {
            val i = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(i)
        }

    }

    private fun userLogin() {

        if (binding.editTxtEmailLogin.text?.isEmpty() == true) {
            binding.editTxtEmailLogin.error = "please enter email"
        } else {
            if (binding.editTxtPasswordLogin.text?.isEmpty() == true) {
                binding.editTxtPasswordLogin.error = "please enter password"
            } else {

                val email = binding.editTxtEmailLogin.text.toString()
                val pass = binding.editTxtPasswordLogin.text.toString()
                val language = "en"
                val plateForm = "mobile"

                binding.loadingDashBoard.loadingBar.visibility = View.VISIBLE
                retrofitCalling(email, pass, language, plateForm)
            }
        }

    }

    private fun retrofitCalling(email: String, pass: String, language: String, plateForm: String) {

        val thread = Thread {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLs.baseURL)
                .build()
                .create(ApiCall::class.java)

            val retrofitData = retrofitBuilder.login(
                email,
                pass,
                plateForm,
                language
            )

            retrofitData.enqueue(
                object : Callback<LoginModel> {
                    override fun onResponse(
                        call: Call<LoginModel>,
                        response: Response<LoginModel>,
                    ) {

                        val loginModel = response.body()
                        val loginData = loginModel?.data!!
                        loginData.accessToken = loginModel.accessToken
                        StaticFields.loginData = loginData
                        if (StaticFields.loginData.status.toInt() == 1) {
                            Log.e("Response:", response.body().toString())
                            sharedPref.saveLogin("LOGIN", true)
                            sharedPref.saveString("TOKEN", StaticFields.loginData.accessToken)
                            binding.loadingDashBoard.loadingBar.visibility = View.GONE
                            val thread = Thread {
                                AppDataBase.getAppDB(this@LoginActivity)?.userDAO()
                                    ?.insertUser(StaticFields.loginData)

                                val i = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(i)
                                finish()
                            }
                            thread.start()
                            thread.join()
                        } else {
                            Log.e("Response:", response.body().toString())
                            binding.loadingDashBoard.loadingBar.visibility = View.GONE
                            CommonFunctions.showDialog(
                                this@LoginActivity,
                                "Ops..!!",
                                loginModel.message,
                                1
                            )
                        }


                    }

                    override fun onFailure(call: Call<LoginModel>, t: Throwable) {

                        binding.loadingDashBoard.loadingBar.visibility = View.GONE
                        CommonFunctions.showDialog(
                            this@LoginActivity,
                            "Ops..!!",
                            t.message!!,
                            1
                        )
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
        //add a Dialog
    }
}