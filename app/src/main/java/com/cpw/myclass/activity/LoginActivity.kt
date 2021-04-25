package com.cpw.myclass.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.cpw.myclass.R
import kotlinx.android.synthetic.main.activity_login.*

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
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}