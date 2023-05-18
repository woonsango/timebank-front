package com.example.android_tutorial_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.util.Log
//WebView 관련 추가 import
import android.webkit.WebViewClient
import android.content.Intent
//splash import
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import java.net.URISyntaxException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webview)

        webView.settings.run {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
        }

        webView.apply {
            webView.webChromeClient = WebChromeClient()
            webView.loadUrl("http://13.125.249.51/")
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                if (request.url.scheme == "intent") {
                    try {
                        // Intent 생성
                        val intent =
                            Intent.parseUri(request.url.toString(), Intent.URI_INTENT_SCHEME)

                        // 실행 가능한 앱이 있으면 앱 실행
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                            Log.d("kakao", "ACTIVITY: ${intent.`package`}")
                            return true
                        }

                        // Fallback URL이 있으면 현재 웹뷰에 로딩
                        val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                        if (fallbackUrl != null) {
                            view.loadUrl(fallbackUrl)
                            return true
                        }
                    } catch (e: URISyntaxException) {
                        Log.e("ERR", "Invalid intent request", e)
                    }
                }
                return false
            }
        }
    }
}

