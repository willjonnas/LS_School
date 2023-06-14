package com.example.lsbusinessschool

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat.startActivity
import com.example.lsbusinessschool.databinding.ActivityEdituserBinding
import com.example.lsbusinessschool.databinding.ActivityForgotpassBinding

class EdituserActivity : AppCompatActivity() {

  
    private lateinit var binding: ActivityEdituserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdituserBinding.inflate(layoutInflater)
        setContentView(binding.root)


    //// LIGAÇÕES A OUTROS SCREENS

    binding.tvCancelar.setOnClickListener {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        //finish()
        }
    }
}
