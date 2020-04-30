package com.gluthfi.farminc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val stat=sharedPreferences.getString("STATUS","")

        if (stat=="1"){

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }

        else {

            btnLogin.setOnClickListener{)

                var email = emailEt.text.toString()
                var password = passwordEt.text.toString()


                postkerserver(email,password)

            }

            btnRegister.setOnClickListener {
                startActivity(Intent(this, Register::class.java))
                finish()
            }
        }
    }

    fun postkerserver(data1:String,data2:String)
    {

        AndroidNetworking.post("http://192.168.100.8/farminc/ceklogin.php")
            .addBodyParameter("email", data1)
            .addBodyParameter("password", data2)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("status"))
                        var statuslogin= jsonObject.optString("status")
//                        txt1.setText("Status : " + statuslogin)


                        if (statuslogin=="1"){

                            val sharedPreferences=getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
                            val editor=sharedPreferences.edit()

                            editor.putString("STATUS",statuslogin)
                            editor.putString("EMAIL", emailEt.text.toString())
                            editor.apply()

                            Toast.makeText(applicationContext,"Welcome!", Toast.LENGTH_LONG).show()

                            startActivity(Intent(this@Login, MainActivity::class.java))
                            finish()
                        } else {
                            Log.i("Uji Coba", "Mandul")
                        }
                    }



                }

                override fun onError(error: ANError) { // handle error
                }

            })

    }
}
