package com.cpw.myclass.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.cpw.myclass.R
import com.cpw.myclass.http.MyCallback
import com.cpw.myclass.http.OkHttpManager
import com.cpw.myclass.http.RequestUrl
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Request
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (!TextUtils.isEmpty(sharedPreferences.getString("student", ""))) {
            et_student_number.setText(sharedPreferences.getString("student", ""))
            if (!TextUtils.isEmpty(sharedPreferences.getString("password", ""))) {
                et_password.setText(sharedPreferences.getString("password", ""))
                cb_remember_password.isChecked = true
            } else {
                cb_remember_password.isChecked = false
            }
        } else {
            cb_remember_password.isChecked = false
        }
        bt_login.setOnClickListener {
            if (TextUtils.isEmpty(et_student_number.text.toString())) {
                Toast.makeText(this, "请输入学号", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_password.text.toString())) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (cb_remember_password.isChecked) {
                editor.putString("student", et_student_number.text.toString())
                editor.putString("password", et_password.text.toString())
            } else {
                editor.putString("student", et_student_number.text.toString())
                editor.putString("password", "")
            }
            editor.apply()
            val param = HashMap<String, String>()
            param["username"] = "love"
            param["password"] = "1231"
            OkHttpManager.instance?.post(RequestUrl.login, param, object : MyCallback{
                override fun onSuccess(json: String) {
                    Log.d("http_test", "success: $json")
                }

                override fun onBefore(request: Request) {
                    Log.d("http_test", "will start")
                }

                override fun onAfter() {
                    Log.d("http_test", "end")
                }

                override fun onFailed(e: IOException) {
                    Log.d("http_test", "failed: ${e.message}")
                }

            })
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}