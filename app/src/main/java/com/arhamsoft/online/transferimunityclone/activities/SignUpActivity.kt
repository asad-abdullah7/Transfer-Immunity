package com.arhamsoft.online.transferimunityclone.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arhamsoft.online.transferimunityclone.databinding.ActivitySignUpBinding
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.ApiCall
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.URLs
import com.arhamsoft.online.transferimunityclone.models.countryModel.CountryModel
import com.arhamsoft.online.transferimunityclone.models.countryModel.CountrySingle
import com.arhamsoft.online.transferimunityclone.models.registerModel.RegisterModel
import com.arhamsoft.online.transferimunityclone.utils.functionsCommon.CommonFunctions
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var countryList: List<CountrySingle>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCountries()
        setIntents()
    }

    private fun getCountries() {

        countryList = ArrayList()
        retrofitCallingForCountries()
    }

    private fun retrofitCallingForCountries() {
        val thread = Thread {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLs.baseURL)
                .build()
                .create(ApiCall::class.java)

            val retrofitData = retrofitBuilder.getCountries()

            retrofitData.enqueue(
                object : Callback<CountryModel> {
                    override fun onResponse(
                        call: Call<CountryModel>,
                        response: Response<CountryModel>,
                    ) {

                        val countryModel = response.body()
                        Log.e("model", "onResponse: $countryModel")

                        if (countryModel != null) {
                            countryList = countryModel.data
                            binding.countrySpinnerLogin.setText(countryList[0].name)
                        }
                    }

                    override fun onFailure(call: Call<CountryModel>, t: Throwable) {

                        CommonFunctions.showDialog(
                            this@SignUpActivity, "Ops..!!",
                            t.message.toString(), 1
                        )

                        Log.e("Error: ", t.toString())
                    }

                },
            )

        }

        thread.start()
        thread.join()
    }

    private fun setIntents() {

        onCLickListeners()

    }

    private fun onCLickListeners() {

        binding.btnSignUp.setOnClickListener {
            registerUser()
        }

        binding.txtRight.setOnClickListener {
            onBackPressed()
        }

        binding.crossImage.setOnClickListener {
            onBackPressed()
        }
        binding.txtPrivacy.setOnClickListener {
            val uri: Uri = Uri.parse(URLs.privacyURL)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        binding.txtTerms.setOnClickListener {
            val uri: Uri = Uri.parse(URLs.termsURL)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.countrySpinnerLogin.setOnClickListener {
            goToCountryActivity()
            Log.e("tag", "onCLickListeners: ${countryList.size}")
        }

    }

    private fun goToCountryActivity() {

        val i = Intent(this, CountryActivity::class.java)
        i.putExtra("list", countryList as ArrayList)
        startActivity(i)
    }

    private fun registerUser() {

        if (binding.editTxtEmailLogin.text?.isEmpty() == true) {
            binding.editTxtEmailLogin.error = "please enter email"
        } else {
            if (binding.editTxtUserNameLogin.text?.isEmpty() == true) {
                binding.editTxtEmailLogin.error = "please enter user name"
            } else {
                if (binding.editTxtPasswordLogin.text?.isEmpty() == true) {
                    binding.editTxtEmailLogin.error = "please enter password"
                } else {
                    if (binding.editTxtConfirmPasswordLogin.text?.isEmpty() == true) {
                        binding.editTxtEmailLogin.error = "please confirm your password"
                    } else {
                        val userName = binding.editTxtUserNameLogin.text.toString()
                        val email = binding.editTxtEmailLogin.text.toString()
                        val pass = binding.editTxtPasswordLogin.text.toString()
                        val confirmPass = binding.editTxtConfirmPasswordLogin.text.toString()
                        val countryName = binding.countrySpinnerLogin.text.toString()
                        val language = "en"
                        val plateForm = "mobile"

                        if (countryName == StaticFields.countryName) {

                            if (pass == confirmPass) {
                                retrofitCalling(
                                    userName,
                                    email,
                                    pass,
                                    confirmPass,
                                    language,
                                    plateForm,
                                    StaticFields.countryId
                                )
                            } else {
                                Toast.makeText(this, "Password is not matched!", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        } else {
                            if (pass == confirmPass) {
                                retrofitCalling(
                                    userName,
                                    email,
                                    pass,
                                    confirmPass,
                                    language,
                                    plateForm,
                                    1
                                )
                            } else {
                                Toast.makeText(this, "Password is not matched!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        }

    }

    private fun retrofitCalling(
        userName: String,
        email: String,
        pass: String,
        confirmPass: String,
        language: String,
        plateForm: String,
        countryId: Int
    ) {

        val thread = Thread {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLs.baseURL)
                .build()
                .create(ApiCall::class.java)

            val retrofitData = retrofitBuilder.register(
                userName, email, pass, confirmPass, countryId, plateForm, language
            )

            Log.e("user", "retrofitCalling: $userName")
            Log.e("user", "retrofitCalling: $email")
            Log.e("user", "retrofitCalling: $pass")
            Log.e("user", "retrofitCalling: $confirmPass")
            Log.e("user", "retrofitCalling: $countryId")
            Log.e("user", "retrofitCalling: $plateForm")
            Log.e("user", "retrofitCalling: $language")

            retrofitData.enqueue(
                object : Callback<RegisterModel> {
                    override fun onResponse(
                        call: Call<RegisterModel>,
                        response: Response<RegisterModel>,
                    ) {

                        val registerModel = response.body()
                        Log.e("model", "onResponse: $registerModel")
                        if (registerModel?.status?.toInt() == 1) {
                            Log.e("Response:", response.body().toString())
                            CommonFunctions.showDialog(
                                this@SignUpActivity,
                                "Thank you for registration..",
                                registerModel.message.toString(),
                                2
                            )
                        } else {
                            Log.e("Response:", response.body().toString())
                            if (registerModel != null) {
                                CommonFunctions.showDialog(
                                    this@SignUpActivity,
                                    "Ops..!!",
                                    "This email address already exists.",
                                    1
                                )
                            }
                        }


                    }

                    override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity, t.message, Toast.LENGTH_SHORT).show()
                        Log.e("Error: ", t.toString())
                    }

                },
            )

        }

        thread.start()
        thread.join()

    }

    override fun onResume() {
        super.onResume()
        if (StaticFields.countryId != 0) {
            binding.countrySpinnerLogin.setText(StaticFields.countryName)
        } else {
            binding.countrySpinnerLogin.setText("Please wait..")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}