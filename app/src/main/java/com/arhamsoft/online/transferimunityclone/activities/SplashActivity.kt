package com.arhamsoft.online.transferimunityclone.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.arhamsoft.online.transferimunityclone.databinding.ActivitySplashBinding
import com.arhamsoft.online.transferimunityclone.receiver.CustomBroadCastReceiver
import com.arhamsoft.online.transferimunityclone.roomDataBase.AppDataBase
import com.arhamsoft.online.transferimunityclone.utils.sharedPreferences.CustomSharedPref
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var sharedPref: CustomSharedPref
    private var mNetworkReceiver: CustomBroadCastReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPref = CustomSharedPref(this)
        sendToLoginScreen()
    }

    private fun sendToLoginScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (sharedPref.isLogin("LOGIN")) {
                val thread = Thread {
                    StaticFields.loginData = AppDataBase.getAppDB(this)?.userDAO()?.getUser()!!
                    val i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                    mNetworkReceiver = CustomBroadCastReceiver()
                    registerReceiver(
                        mNetworkReceiver,
                        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                    )
                    finish()
                }
                thread.start()
                thread.join()
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }, 800)

    }
}