package com.example.lsbusinessschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lsbusinessschool.database.DatabaseHelper
import com.example.lsbusinessschool.databinding.ActivityRegisterBinding
import com.example.lsbusinessschool.model.UserModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        databaseHelper = DatabaseHelper.getDatabase(applicationContext)

        binding.buttonRegistar.setOnClickListener {
            val username = binding.insertUsername.text.toString()
            val password = binding.insertPassword.text.toString()
            //val passwordConf = binding.etPasswordConf.text.toString()

            if (username != "" && password != "") {
                val userModel =
                    UserModel(username = username, password = password)

                val res = databaseHelper.appDao()?.register(userModel)

                if (res != null) {
                    if (res > 0) {
                        Toast.makeText(applicationContext, "Registration Done!", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Registration Failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }








        //// LIGAÇÕES A OUTROS SCREENS


        binding.tvCancelar.setOnClickListener{
            //intent = Intent(this,LoginActivity::class.java)
            //startActivity(intent)
            finish()
        }
    }
}