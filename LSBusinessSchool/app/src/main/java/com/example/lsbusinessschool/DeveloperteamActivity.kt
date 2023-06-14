package com.example.lsbusinessschool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lsbusinessschool.databinding.ActivityDeveloperteamBinding
import com.example.lsbusinessschool.databinding.ActivityLoginBinding

class DeveloperteamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeveloperteamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeveloperteamBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //// LIGAÇÕES A OUTROS SCREENS

        binding.tvSairdevteam.setOnClickListener{
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}