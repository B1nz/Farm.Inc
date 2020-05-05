package com.gluthfi.farminc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_produk.*
import kotlinx.android.synthetic.main.produk_list.*

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
    }

    private fun onEditMode(){

        idPa.setText(i.getStringExtra("id"))
        imagePa.setText(i.getStringExtra("image"))
        namaPa.setText(i.getStringExtra("nama"))
        hargaPa.setText(i.getStringExtra("harga"))
        idPa.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE

//        kategori = i.getStringExtra("kategori")
//
//        if(kategori.equals("Buah")){
//            rgKategori.check(R.id.radioBuah)
//        }else{
//            rgKategori.check(R.id.radioSayur)
//        }

    }
}
