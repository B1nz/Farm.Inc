package com.gluthfi.farminc

import CustomAdapter
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

        val sRecyclerView = findViewById(R.id.sRecyclerView) as RecyclerView
        sRecyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sRecyclerView.setHasFixedSize(true)

        backSrch.setOnClickListener {

            val sharedPreferences=getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("CARI","")
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

        val sharedPreferences = getSharedPreferences("SEARCH", Context.MODE_PRIVATE)
        val cari=sharedPreferences.getString("CARI","")

        val searchPrdk = ArrayList<Produk>()

        AndroidNetworking.post(ApiEndPoint.SEARCH)
            .addBodyParameter("search", cari)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    val jsonArray = response.getJSONArray("result")

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        var idPrdk = jsonObject.optString("id_produk").toString()
                        var imagePrdk = jsonObject.optString("image").toString()
                        var namaPrdk = jsonObject.optString("nama_produk").toString()
                        var hargaPrdk = jsonObject.optString("harga").toString()

                        searchPrdk.add(Produk("$idPrdk", "$imagePrdk", "$namaPrdk", "$hargaPrdk"))

                    }

                    val adapter = CustomAdapter(applicationContext, searchPrdk)
                    sRecyclerView.adapter=adapter

                }

                override fun onError(anError: ANError?) {
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
                }

            })

    }

//    override fun onResume() {
//        super.onResume()
//        loadSearch()
//    }
//
//    private fun loadSearch() {
//    }

//    override fun onItemClick(produk: Produk, position: Int) {
//        Toast.makeText(this, produk.nama, Toast.LENGTH_SHORT).show()
//    }
}
