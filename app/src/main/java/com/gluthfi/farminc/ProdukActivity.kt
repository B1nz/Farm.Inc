package com.gluthfi.farminc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.android.synthetic.main.activity_produk.*
import kotlinx.android.synthetic.main.produk_list.*
import org.json.JSONArray
import java.time.LocalDateTime

class ProdukActivity : AppCompatActivity() {

    lateinit var i: Intent
    private var kategori = "Sayur"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk)

        i = intent

        if(i.hasExtra("editmode")){

            if(i.getStringExtra("editmode").equals("1")){
                onEditMode()
            }
        }

        rgKategori.setOnCheckedChangeListener { radioGroup, i ->

            when(i){

                R.id.radioBuah->{
                    kategori = "Buah"
                }

                R.id.radioSayur->{
                    kategori = "Sayur"
                }

            }

        }

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val id_pengguna=sharedPreferences.getString("ID","")

        iduser.setText(id_pengguna)

        btnUpdate.setOnClickListener {
            update()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnDelete.setOnClickListener {
            delete()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnCreate.setOnClickListener {
            create()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun create() {

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val id=sharedPreferences.getString("ID","")

        AndroidNetworking.post(ApiEndPoint.ADD_PRODUCT)
            .addBodyParameter("kategori", kategori)
            .addBodyParameter("nama_produk", namaPa.text.toString())
            .addBodyParameter("deskripsi", deskripsiPa.text.toString())
            .addBodyParameter("harga", hargaPa.text.toString())
            .addBodyParameter("image", imagePa.text.toString())
            .addBodyParameter("pengguna_id", id)
            .addBodyParameter("created_at", LocalDateTime.now().toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    Log.i("Uji Coba", "Sukses")
                }

                override fun onError(anError: ANError?) {
                    Log.i("Uji Coba", "Mandul")
                }
            })
    }

    private fun onEditMode(){

        idPa.setText(i.getStringExtra("id"))
        imagePa.setText(i.getStringExtra("image"))
        namaPa.setText(i.getStringExtra("nama"))
        hargaPa.setText(i.getStringExtra("harga"))
        deskripsiPa.setText(i.getStringExtra("deskripsi"))
        idPa.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE

        kategori = i.getStringExtra("kategori")

        if(kategori.equals("Buah")){
            rgKategori.check(R.id.radioBuah)
        }else{
            rgKategori.check(R.id.radioSayur)
        }

    }

    private fun update() {
        AndroidNetworking.post(ApiEndPoint.EDIT_PRODUCT)
            .addBodyParameter("id_produk", i.getStringExtra("id"))
            .addBodyParameter("kategori", kategori)
            .addBodyParameter("nama_produk", namaPa.text.toString())
            .addBodyParameter("deskripsi", deskripsiPa.text.toString())
            .addBodyParameter("harga", hargaPa.text.toString())
            .addBodyParameter("image", imagePa.text.toString())
            .addBodyParameter("updated_at", LocalDateTime.now().toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    Log.i("Uji Coba", "Sukses")
                }

                override fun onError(anError: ANError?) {
                    Log.i("Uji Coba", "Mandul")
                }
            })
    }

    private fun delete() {
        AndroidNetworking.post(ApiEndPoint.DELETE_PRODUCT)
            .addBodyParameter("id_produk", i.getStringExtra("id"))
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    Log.i("Uji Coba", "Sukses")
                }

                override fun onError(anError: ANError?) {
                    Log.i("Uji Coba", "Mandul")
                }
            })
    }
}
