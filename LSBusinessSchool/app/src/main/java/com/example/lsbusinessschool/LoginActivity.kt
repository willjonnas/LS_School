package com.example.lsbusinessschool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.lsbusinessschool.database.DatabaseHelper
import com.example.lsbusinessschool.databinding.ActivityLoginBinding
import java.time.LocalDate

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper.getDatabase(applicationContext)



        //// LIGAÇÕES A OUTROS SCREENS

        binding.tvSignup.setOnClickListener{
            intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            //finish()
        }

        binding.tvForgotpass.setOnClickListener{
            intent = Intent(this,ForgotpassActivity::class.java)
            startActivity(intent)
            //  finish()
        }


        //// AINDA SEM VALIDAÇÃO DE REGISTO


        binding.buttonLogin.setOnClickListener {
            val password = binding.tvLoginpass.text.toString()
            val username = binding.tvLoginuser.text.toString()

            val user = databaseHelper.appDao()?.login(username, password)

            if (user != null) {
                /// VER APLICAÇÃO DE TOKEN NO FINAL
                /*val generatedToken = generateToken()
                keepLoggedIn(sharedPreferences, username, generatedToken)

                user.token_auth = generatedToken
                user.token_data = LocalDate.now().plusDays(1).toString()
                val res = databaseHelper.appDao()?.refreshToken(user)
                //Toast.makeText(applicationContext, res.toString(), Toast.LENGTH_SHORT).show()*/
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                //finish()
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Wrong Name or Password!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        //// ENTRADA LOGIN SEM VALIDAÇÃO DE REGISTO EM BD

        /*binding.buttonLogin.setOnClickListener{
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            //finish()
        }*/

/*
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)*/
    }
}