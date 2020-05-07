package com.gluthfi.farminc

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_produk.*
import org.json.JSONArray
import java.time.LocalDateTime

class ProdukActivity : AppCompatActivity(){

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
                R.id.radioBiji->{
                    kategori = "Biji"
                }
                R.id.radioRempah->{
                    kategori = "Rempah"
                }
                R.id.radioUmbi->{
                    kategori = "Umbi"
                }
            }
        }

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val id_pengguna=sharedPreferences.getString("ID","")

        iduser.setText(id_pengguna)

        btnUpdate.setOnClickListener {
            update()

            startActivity(Intent(this, AdminProdukActivity::class.java))
            finish()
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Hapus data ini ?")
                .setPositiveButton("HAPUS", DialogInterface.OnClickListener { dialogInterface, i ->
                    delete()

                    startActivity(Intent(this, AdminProdukActivity::class.java))
                    finish()
                })
                .setNegativeButton("BATAL",DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .show()
        }

        btnCreate.setOnClickListener {
            create()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    @SuppressLint("NewApi")
    private fun create() {

        val loading = ProgressDialog(this)
        loading.setMessage("Menambahkan data...")
        loading.show()

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val pengguna_id=sharedPreferences.getString("ID","")

        AndroidNetworking.post(ApiEndPoint.ADD_PRODUCT)
            .addBodyParameter("pengguna_id", pengguna_id)
            .addBodyParameter("kategori", kategori)
            .addBodyParameter("nama_produk", namaPa.text.toString())
            .addBodyParameter("deskripsi", deskripsiPa.text.toString())
            .addBodyParameter("harga", hargaPa.text.toString())
            .addBodyParameter("image", imagePaP.text.toString())
            .addBodyParameter("created_at", LocalDateTime.now().toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    loading.dismiss()
                    Log.i("Uji Coba", "Sukses")
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun onEditMode(){

        idPa.setText(i.getStringExtra("id"))
        imagePaP.setText(i.getStringExtra("image"))
        namaPa.setText(i.getStringExtra("nama"))
        hargaPa.setText(i.getStringExtra("harga"))
        deskripsiPa.setText(i.getStringExtra("deskripsi"))

        Glide.with(this)
            .load(i.getStringExtra("image"))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imagePa)

        idPa.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE
        imagePa.visibility = View.VISIBLE

        kategori = i.getStringExtra("kategori")

        if(kategori.equals("Buah")){
            rgKategori.check(R.id.radioBuah)
        }else if(kategori.equals("Sayur")){
            rgKategori.check(R.id.radioSayur)
        }else if(kategori.equals("Biji")){
            rgKategori.check(R.id.radioBiji)
        }else if(kategori.equals("Rempah")){
            rgKategori.check(R.id.radioRempah)
        }else {
            rgKategori.check(R.id.radioUmbi)
        }

    }

    @SuppressLint("NewApi")
    private fun update() {
        AndroidNetworking.post(ApiEndPoint.EDIT_PRODUCT)
            .addBodyParameter("id_produk", i.getStringExtra("id"))
            .addBodyParameter("kategori", kategori)
            .addBodyParameter("nama_produk", namaPa.text.toString())
            .addBodyParameter("deskripsi", deskripsiPa.text.toString())
            .addBodyParameter("harga", hargaPa.text.toString())
            .addBodyParameter("image", imagePaP.text.toString())
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
