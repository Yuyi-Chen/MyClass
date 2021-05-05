package com.cpw.myclass.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.cpw.myclass.R
import com.cpw.myclass.data.NormalResult
import com.cpw.myclass.data.UserBean
import com.cpw.myclass.data.UserQuestionBean
import com.cpw.myclass.http.MyCallback
import com.cpw.myclass.http.OkHttpManager
import com.cpw.myclass.http.RequestUrl
import com.cpw.myclass.util.CommonUtil
import com.google.gson.Gson
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
                showError("请输入学号")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_password.text.toString())) {
                showError("请输入密码")
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
            param["password"] = et_password.text.toString()
            param["user_id"] = et_student_number.text.toString()
            OkHttpManager.instance?.post(RequestUrl.login, param, object : MyCallback{
                override fun onSuccess(json: String) {
                    Log.d("http_test", "success: $json")
                    val gson = Gson()
                    val result = gson.fromJson<NormalResult>(json, NormalResult::class.java)
                    if (result.code == "200") {
                        getUser(this@LoginActivity)
                        getQuestion(true)
                    } else {
                        showError(result.message)
                    }
                }

                override fun onBefore(request: Request) {
                    Log.d("http_test", "will start")
                }

                override fun onAfter() {
                    Log.d("http_test", "end")
                }

                override fun onFailed(e: IOException) {
                    Log.d("http_test", "failed: ${e.message}")
                    showError(e.message.toString())
                }
            })
        }
    }

    companion object {
        fun getUser(context: Context) {
            val param = HashMap<String, String?>()
            val sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE)
            param["user_id"] = sharedPreferences.getString("student", "")
            OkHttpManager.instance?.post(RequestUrl.getUserMessage, param, object : MyCallback {
                override fun onSuccess(json: String) {
                    Log.d("http_test", "success: $json")
                    val gson = Gson()
                    val result = gson.fromJson<UserBean>(json, UserBean::class.java)
                    CommonUtil.currentUser = result
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
        }
    }

    private fun getQuestion(isLogin: Boolean) {
        val param = HashMap<String, String>()
        param["user_id"] = et_student_number.text.toString()
        OkHttpManager.instance?.post(RequestUrl.getQuestion, param, object : MyCallback{
            override fun onSuccess(json: String) {
                Log.d("http_test", "success: $json")
                val gson = Gson()
                val result = gson.fromJson<UserQuestionBean>(json, UserQuestionBean::class.java)
                if (isLogin) {
                    if (result.question == " ") {
                        val intent = Intent(this@LoginActivity, QuestionActivity::class.java)
                        intent.putExtra("user_id", et_student_number.text.toString())
                        startActivity(intent)
                    } else {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }
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
    }

    private val runnable: Runnable = Runnable {
        tv_error.text = ""
    }

    private fun showError(error: String) {
        tv_error.removeCallbacks(runnable)
        tv_error.text = error
        tv_error.postDelayed(runnable, 3000)
    }
}