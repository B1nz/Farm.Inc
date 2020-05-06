package com.gluthfi.farminc

import AdminAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_admin_produk.*
import kotlinx.android.synthetic.main.activity_search.*
import org.json.JSONObject
import java.util.ArrayList

class AdminProdukActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_produk)

        backAdm.setOnClickListener {

            val sharedPreferencesA=getSharedPreferences("ADMIN", Context.MODE_PRIVATE)
            val editor=sharedPreferencesA.edit()

            editor.putString("CARI","")
            editor.putString("KATEGORI","")
            editor.apply()


            startActivity(Intent(this,ProfileActivity::class.java))
            finish()
        }

        searchAdm.setOnClickListener {

            val sharedPreferencesA=getSharedPreferences("ADMIN", Context.MODE_PRIVATE)
            val editor=sharedPreferencesA.edit()

            editor.putString("CARI",searchEAdm.text.toString())
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        allAdm.setOnClickListener {
            val sharedPreferencesA=getSharedPreferences("ADMIN", Context.MODE_PRIVATE)
            val editor=sharedPreferencesA.edit()

            editor.putString("CARI","")
            editor.putString("KATEGORI","")
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        buahAdm.setOnClickListener {
            val sharedPreferencesA=getSharedPreferences("ADMIN", Context.MODE_PRIVATE)
            val editor=sharedPreferencesA.edit()

            editor.putString("KATEGORI","buah")
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        sayurAdm.setOnClickListener {
            val sharedPreferencesA=getSharedPreferences("ADMIN", Context.MODE_PRIVATE)
            val editor=sharedPreferencesA.edit()

            editor.putString("KATEGORI","sayur")
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        apSwipe.setOnRefreshListener {
            getData()
        }

        getData()

    }

    private fun getData() {

        apSwipe.isRefreshing = true

        val sRecyclerView = findViewById(R.id.sRecyclerView) as RecyclerView
        sRecyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sRecyclerView.setHasFixedSize(true)

        val sharedPreferencesA = getSharedPreferences("ADMIN", Context.MODE_PRIVATE)
        val cari = sharedPreferencesA.getString("CARI","")
        var kategori = sharedPreferencesA.getString("KATEGORI","")

        val searchPrdk = ArrayList<Produk>()

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val pengguna_id=sharedPreferences.getString("ID","")

        AndroidNetworking.post(ApiEndPoint.ADMIN_PRODUK)
            .addBodyParameter("pengguna_id", pengguna_id)
            .addBodyParameter("search", cari)
            .addBodyParameter("kategori", kategori)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    apSwipe.isRefreshing = false

                    val jsonArray = response.getJSONArray("result")

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        var idPrdk = jsonObject.optString("id_produk").toString()
                        var imagePrdk = jsonObject.optString("image").toString()
                        var namaPrdk = jsonObject.optString("nama_produk").toString()
                        var hargaPrdk = jsonObject.optString("harga").toString()
                        var penggunaPrdk = jsonObject.optString("pengguna_id").toString()
                        var deskripsiPrdk = jsonObject.optString("deskripsi").toString()
                        var kategoriPrdk = jsonObject.optString("kategori").toString()

                        searchPrdk.add(Produk("$idPrdk", "$imagePrdk", "$namaPrdk", "$hargaPrdk", "$penggunaPrdk", "$deskripsiPrdk", "$kategoriPrdk"))

                    }

                    val adapter = AdminAdapter(applicationContext, searchPrdk)
                    sRecyclerView.adapter=adapter

                }

                override fun onError(anError: ANError?) {
                    apSwipe.isRefreshing = false
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()
                }

            })
    }
}
