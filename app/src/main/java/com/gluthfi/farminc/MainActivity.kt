package com.gluthfi.farminc

import CustomAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val nama=sharedPreferences.getString("NAMA","")
        val id=sharedPreferences.getString("ID","")

        userwelcome.setText(nama)
        idwell.setText(id)

        profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        tambah.setOnClickListener {
            startActivity(Intent(this, AddProduct::class.java))
        }

        searchBtn.setOnClickListener {
            val sharedPreferences=getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("CARI","")
            editor.apply()

            startActivity(Intent(this, SearchActivity::class.java))
        }

        searchMain.setOnClickListener {

            val sharedPreferences=getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("CARI",searchEt.text.toString())
            editor.apply()

            startActivity(Intent(this, SearchActivity::class.java))
        }

        val recyclerView = findViewById(R.id.mRecyclerView) as RecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        val freshPrdk = ArrayList<Produk>()

        AndroidNetworking.get(ApiEndPoint.FRESH)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        var idPrdk = jsonObject.optString("id_produk").toString()
                        var imagePrdk = jsonObject.optString("image").toString()
                        var namaPrdk = jsonObject.optString("nama_produk").toString()
                        var hargaPrdk = jsonObject.optString("harga").toString()

                        freshPrdk.add(Produk("$idPrdk", "$imagePrdk", "$namaPrdk", "$hargaPrdk"))

                    }

                    val adapter = CustomAdapter(applicationContext, freshPrdk)
                    recyclerView.adapter=adapter

                }

                override fun onError(anError: ANError?) {
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
                }

            })
    }

//    override fun onResume() {
//        super.onResume()
//        loadFresh()
//    }
//
//    private fun loadFresh() {
//
//
//    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
