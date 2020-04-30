package com.gluthfi.farminc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val email=sharedPreferences.getString("EMAIL","")

        userwelcome.setText(email)

        logout.setOnClickListener{
            val sharedPreferences=getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("STATUS","0")
            editor.putString("EMAIL", "NULL")
            editor.apply()

            Toast.makeText(applicationContext,"User logout", Toast.LENGTH_LONG).show()

            startActivity(Intent(this,Login::class.java))
            finish()
        }
    }
}
