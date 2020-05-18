package com.gluthfi.farminc

import UserAdapter
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this, ProdukActivity::class.java))
        }

        val fab2 = findViewById<FloatingActionButton>(R.id.fab2)
        fab2.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }



        searchMain.setOnClickListener {

            val sharedPreferences=getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("CARI",searchEt.text.toString())
            editor.putString("KATEGORI","")
            editor.apply()

            startActivity(Intent(this, SearchActivity::class.java))

            searchEt.setText("")
        }

        allBtn.setOnClickListener {

            val sharedPreferences=getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("CARI",searchEt.text.toString())
            editor.putString("KATEGORI","")
            editor.apply()

            startActivity(Intent(this, SearchActivity::class.java))

            searchEt.setText("")
        }

        swipeRefresh.setOnRefreshListener {
            getFresh()
        }

        getFresh()

    }

    private fun getFresh() {

        swipeRefresh.isRefreshing = true

        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        val recyclerView = findViewById(R.id.mRecyclerView) as RecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        val freshPrdk = ArrayList<Produk>()

        AndroidNetworking.get(ApiEndPoint.FRESH)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    loading.dismiss()

                    swipeRefresh.isRefreshing = false

                    Log.e("_kotlinResponse", response.toString())

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

                        freshPrdk.add(Produk("$idPrdk", "$imagePrdk", "$namaPrdk", "$hargaPrdk", "$penggunaPrdk", "$deskripsiPrdk", "$kategoriPrdk"))

                    }

                    val adapter = UserAdapter(applicationContext, freshPrdk)
                    recyclerView.adapter=adapter

                }

                override fun onError(anError: ANError?) {

                    swipeRefresh.isRefreshing = false

                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
                }

            })
    }

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