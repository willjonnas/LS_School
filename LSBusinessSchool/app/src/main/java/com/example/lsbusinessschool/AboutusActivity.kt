package com.example.lsbusinessschool

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewAssetLoader
import com.example.lsbusinessschool.databinding.ActivityAboutusBinding


class AboutusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutusBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutusBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.webView.loadUrl("file:///android_asset/FichaTecnica.html")


        //// LIGAÇÕES A OUTROS SCREENS

        binding.tvSairaboutus.setOnClickListener{
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}