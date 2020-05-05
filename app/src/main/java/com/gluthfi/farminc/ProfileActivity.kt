package com.gluthfi.farminc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        profileUpdate.setOnClickListener {
            startActivity(Intent(this, ProfileUpdate::class.java))
            finish()
        }

        logoutBtn.setOnClickListener{
            val sharedPreferences=getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("STATUS","0")
            editor.putString("NAMA", "NULL")
            editor.putString("ID", "NULL")
            editor.apply()

            Toast.makeText(applicationContext,"User logout", Toast.LENGTH_LONG).show()

            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val id_pengguna=sharedPreferences.getString("ID","")

        AndroidNetworking.post(ApiEndPoint.PROFILE)
            .addBodyParameter("id_pengguna", id_pengguna)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        namaProfile.setText(jsonObject.optString("nama"))
                        emailProfile.setText(jsonObject.optString("email"))
                        passwordProfile.setText(jsonObject.optString("password"))
                        alamatProfile.setText(jsonObject.optString("alamat"))
                        nohpProfile.setText(jsonObject.optString("nohp"))

                    }
                }

                override fun onError(error: ANError) { // handle error
                }

            })
    }
}
