package com.gluthfi.farminc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile_update.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime

class ProfileUpdate : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_update)

        updateBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val email=sharedPreferences.getString("EMAIL","")

        AndroidNetworking.post("http://192.168.100.8/farminc/showuser.php")
            .addBodyParameter("email", email)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        namaUpdate.setText(jsonObject.optString("nama"))
                        emailUpdate.setText(jsonObject.optString("email"))
                        passwordUpdate.setText(jsonObject.optString("password"))
                        alamatUpdate.setText(jsonObject.optString("alamat"))
                        nohpUpdate.setText(jsonObject.optString("nohp"))

                    }
                }

                override fun onError(error: ANError) { // handle error
                }

            })

        updateBtn.setOnClickListener {
            var data1 = namaUpdate.text.toString()
            var data2 = email.toString()
            var data3 = passwordUpdate.text.toString()
            var data4 = alamatUpdate.text.toString()
            var data5 = nohpUpdate.text.toString()
            val data6 = LocalDateTime.now().toString()

            postkeserver(data1, data2, data3, data4, data5, data6)

            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }

    fun postkeserver(data1:String, data2:String, data3:String, data4:String, data5:String, data6: String) {
        AndroidNetworking.post("http://192.168.100.8/farminc/profileupdate.php")
            .addBodyParameter("nama", data1)
            .addBodyParameter("email", data2)
            .addBodyParameter("password", data3)
            .addBodyParameter("alamat", data4)
            .addBodyParameter("nohp", data5)
            .addBodyParameter("updated_at", data6)
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
