package com.arhamsoft.online.transferimunityclone.utils

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout

class WebClientApi(private val url2: String, private var loadingBar: RelativeLayout) :
    WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.d("url1", url.toString())
        loadingBar.visibility = View.VISIBLE
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url2)
        Log.d("url2", url2)
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.d("url3", url.toString())
        loadingBar.visibility = View.GONE
    }

}