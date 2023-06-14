package com.example.lsbusinessschool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lsbusinessschool.databinding.ActivityForgotpassBinding
import com.example.lsbusinessschool.databinding.ActivityRegisterBinding

class ForgotpassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotpassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotpassBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonRecvrpass.setOnClickListener {
            val emailAddress = binding.tvEmailrecvr.text.toString()
            if (emailAddress.isNotEmpty()) {
                val emailIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                    putExtra(Intent.EXTRA_SUBJECT, "Password Recovery")
                    putExtra(Intent.EXTRA_TEXT, "Please reset my password.")
                }
                Toast.makeText(this, "Email sent. Please check your email.", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent.createChooser(emailIntent, "Send email"))
                finish()
            } else {
                Toast.makeText(this, "You must provide a valid email", Toast.LENGTH_SHORT).show()
            }
        }

        //// LIGAÇÕES A OUTROS SCREENS

        binding.tvCancelarrecpass.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}