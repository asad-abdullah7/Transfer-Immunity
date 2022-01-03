package com.arhamsoft.online.transferimunityclone.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings.PluginState
import androidx.appcompat.app.AppCompatActivity
import com.arhamsoft.online.transferimunityclone.databinding.ActivityDashboardBinding
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.URLs
import com.arhamsoft.online.transferimunityclone.utils.WebClientApi


class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWebViewData()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebViewData() {

        val id = intent.extras?.get("user_id")
        val lang = intent.extras?.get("lang")
        val check = intent.extras?.getInt("check")
        var url = ""
        url = if (check == 0) {
            //dashboard
            URLs.dashBoardURL + "user_id=$id&" + "direct_login=1&lang=$lang"
        } else {
            //Upgrade Package
            URLs.packagesURL + "user_id=$id&" + "direct_login=1&lang=$lang"

        }

        Log.e("tag", "setWebViewData: $url")

        binding.dashboardWeb.settings.useWideViewPort = true
        binding.dashboardWeb.settings.domStorageEnabled = true
        binding.dashboardWeb.settings.javaScriptEnabled = true
        binding.dashboardWeb.settings.pluginState = PluginState.ON
        binding.dashboardWeb.webViewClient = WebClientApi(url, binding.loadingDashBoard.loadingBar)
        binding.dashboardWeb.loadUrl(url)
    }
}