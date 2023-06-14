package com.example.lsbusinessschool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewbinding.ViewBinding
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.daimajia.slider.library.Tricks.ViewPagerEx
import com.example.lsbusinessschool.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSlider()

        ////código ativação do menu bar

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //// LIGAÇÕES A OUTROS SCREENS MENU BAR




        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.button_useredit -> {
                    intent = Intent(this, EdituserActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_turmas -> {
                    intent = Intent(this, CourseMainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_alunos -> {
                    intent = Intent(this, StudentMainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_weather -> {
                    intent = Intent(this, WeatherActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_contacts -> {
                    intent = Intent(this, ContactsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_logout -> {
                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.tv_aboutus -> {
                    intent = Intent(this, AboutusActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.tv_dev_team -> {
                    intent = Intent(this, DeveloperteamActivity::class.java)
                    startActivity(intent)
                    finish()
                }


            }

            true
        }




    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {

            return true
        }

        return super.onOptionsItemSelected(item)
    }


    ///// IMAGE SLIDER

    val img = listOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
        R.drawable.f
    )



    private fun getSlider() {
        binding.slider.setDuration(4000)
        for (item in 0 until img.count()){

            val textSliderView = TextSliderView(this).apply {
                image(img[item])
                //setOnSliderClickListener(this@MainActivity)
                scaleType = BaseSliderView.ScaleType.FitCenterCrop
            }

            binding.slider.addSlider(textSliderView)
        }
    }

    override fun onStop() {
        binding.slider.stopAutoCycle()
        super.onStop()
    }

    override fun onSliderClick(slider: BaseSliderView?) {
        Toast.makeText(this,"test",Toast.LENGTH_SHORT).show()
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {

    }



}