package com.gluthfi.farminc

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.produk_list.view.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        i = intent

        if(i.hasExtra("editmode")){

            if(i.getStringExtra("editmode").equals("1")){
                onEditMode()
                getData()
            }
        }

        backDt.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun onEditMode(){

        idDt.setText(i.getStringExtra("id"))
        namaDt.setText(i.getStringExtra("nama"))
        hargaDt.setText(i.getStringExtra("harga"))
        deskripsiDt.setText(i.getStringExtra("deskripsi"))
        kategoriDt.setText(i.getStringExtra("kategori"))

        Glide.with(this)
            .load(i.getStringExtra("image"))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageDt)

        idDt.isEnabled = false

    }

    private fun getData() {

        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()


        var id = i.getStringExtra("id")

        AndroidNetworking.post(ApiEndPoint.GETDATA)
            .addBodyParameter("id_produk", id)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    loading.dismiss()

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        noDt.setText(jsonObject.optString("nohp"))
                        alamatDt.setText(jsonObject.optString("alamat"))

                    }
                }

                override fun onError(error: ANError) { // handle error
                    loading.dismiss()
                }

            })

    }
}