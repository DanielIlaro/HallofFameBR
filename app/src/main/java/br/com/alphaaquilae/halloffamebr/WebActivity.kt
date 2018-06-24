package br.com.alphaaquilae.halloffamebr

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.webkit.HttpAuthHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import java.net.URL
import java.util.*

/**
 * Autor: Daniel Ilaro da Silva
 * Data: 20/02/18.
 */
class WebActivity : AppCompatActivity() {

    lateinit var webNav:WebView
    lateinit var url:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        webNav = findViewById(R.id.webNav)

        url = intent.getStringExtra("url")

        webNav.webViewClient = object:WebViewClient(){
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                if (!view!!.url.equals(url)){
                    webNav.webViewClient = null
                }
            }

        }
        webNav.settings.javaScriptEnabled = true
        webNav.loadUrl(url)

    }

    override fun onBackPressed(){
        startActivity(Intent(this@WebActivity, MainActivity::class.java))
    }

}
