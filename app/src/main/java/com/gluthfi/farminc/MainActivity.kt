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
        val id=sharedPreferences.getString("ID","")

        userwelcome.setText(email)
        idwell.setText(id)

        profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        tambah.setOnClickListener {
            startActivity(Intent(this, AddProduct::class.java))
            finish()
        }
    }
}
