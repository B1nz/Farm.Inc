package com.gluthfi.farminc

import UserAdapter
import android.annotation.SuppressLint
import android.app.ProgressDialog
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
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.searchEt
import org.json.JSONObject
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        backSrch.setOnClickListener {

            val sharedPreferences=getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("CARI","")
            editor.putString("KATEGORI","")
            editor.apply()


            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        searchSrch.setOnClickListener {

            val sharedPreferences=getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("CARI",searchEt.text.toString())
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        allSrch.setOnClickListener {
            val sharedPreferences=getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("CARI","")
            editor.putString("KATEGORI","")
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        buahSrch.setOnClickListener {
            val sharedPreferences=getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("KATEGORI","buah")
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        sayurSrch.setOnClickListener {
            val sharedPreferences = getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("KATEGORI", "sayur")
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        rempahSrch.setOnClickListener {
            val sharedPreferences = getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("KATEGORI", "rempah")
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        umbiSrch.setOnClickListener {
            val sharedPreferences = getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("KATEGORI", "umbi")
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        bijiSrch.setOnClickListener {
            val sharedPreferences = getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("KATEGORI", "biji")
            editor.apply()

            startActivity(Intent(getIntent()))
            finish()
        }

        searchSwipe.setOnRefreshListener {
            getData()
        }

        getData()

    }

    private fun getData() {

        searchSwipe.isRefreshing = true

        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        val sRecyclerView = findViewById(R.id.sRecyclerView) as RecyclerView
        sRecyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sRecyclerView.setHasFixedSize(true)

        val sharedPreferences = getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
        val cari = sharedPreferences.getString("CARI","")
        var kategori = sharedPreferences.getString("KATEGORI","")

        val searchPrdk = ArrayList<Produk>()

        AndroidNetworking.post(ApiEndPoint.SEARCH)
            .addBodyParameter("search", cari)
            .addBodyParameter("kategori", kategori)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    loading.dismiss()

                    searchSwipe.isRefreshing = false

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

                    val adapter = UserAdapter(applicationContext, searchPrdk)
                    sRecyclerView.adapter=adapter

                }

                override fun onError(anError: ANError?) {
                    searchSwipe.isRefreshing = false
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
                }

            })
    }
}
