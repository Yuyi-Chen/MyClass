package com.cpw.myclass.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.cpw.myclass.R
import com.cpw.myclass.data.NormalResult
import com.cpw.myclass.http.MyCallback
import com.cpw.myclass.http.OkHttpManager
import com.cpw.myclass.http.RequestUrl
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_question.*
import okhttp3.Request
import java.io.IOException

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        bt_save.setOnClickListener {
            if (TextUtils.isEmpty(et_question.text.toString())) {
                Toast.makeText(this, "请输入问题", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_answer.text.toString())) {
                Toast.makeText(this, "请输入答案", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val param = HashMap<String, String>()
            param["user_question"] = et_question.text.toString()
            param["user_answer"] = et_answer.text.toString()
            param["user_id"] = intent.getStringExtra("user_id") ?: ""
            OkHttpManager.instance?.post(RequestUrl.setQuestion, param, object : MyCallback{
                override fun onSuccess(json: String) {
                    startActivity(Intent(this@QuestionActivity, MainActivity::class.java))
                    finish()
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
}