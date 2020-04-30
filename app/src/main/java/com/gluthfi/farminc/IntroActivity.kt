package com.gluthfi.farminc

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    lateinit var preference : SharedPreferences
    val pref_show_intro = "Intro"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        preference = getSharedPreferences("IntroSlider", Context.MODE_PRIVATE)

        if (!preference.getBoolean(pref_show_intro, true)) {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        introBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()

            val editor = preference.edit()
            editor.putBoolean(pref_show_intro, false)
            editor.apply()
        }
    }
}
