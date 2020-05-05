package com.gluthfi.farminc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONArray
import java.time.LocalDateTime

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegisterReg.setOnClickListener {
            var data1 = namaReg.text.toString()
            var data2 = emailReg.text.toString()
            var data3 = passwordReg.text.toString()
            var data4 = alamatReg.text.toString()
            var data5 = hpReg.text.toString()
            val data6 = LocalDateTime.now().toString()

            postkeserver(data1, data2, data3, data4, data5, data6)

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnBackReg.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    fun postkeserver(data1:String, data2:String, data3:String, data4:String, data5:String, data6: String) {
        AndroidNetworking.post(ApiEndPoint.REGISTER)
            .addBodyParameter("nama", data1)
            .addBodyParameter("email", data2)
            .addBodyParameter("password", data3)
            .addBodyParameter("alamat", data4)
            .addBodyParameter("nohp", data5)
            .addBodyParameter("created_at", data6)
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
