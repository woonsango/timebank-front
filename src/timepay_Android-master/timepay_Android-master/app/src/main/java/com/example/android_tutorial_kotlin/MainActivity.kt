package com.example.android_tutorial_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView

//WebView 관련 추가 import
import android.webkit.WebViewClient

//splash import
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

//public static WebView webView:

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webview)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportMultipleWindows(true)
        }

        webView.apply {
            webView.webChromeClient = WebChromeClient()
            webView.loadUrl("http://13.125.249.51/")
        }
    }




}

