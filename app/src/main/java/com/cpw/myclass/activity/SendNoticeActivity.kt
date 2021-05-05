package com.cpw.myclass.activity

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
import com.cpw.myclass.util.CommonUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_send_notice.*
import okhttp3.Request
import java.io.IOException

class SendNoticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_notice)
        iv_back.setOnClickListener {
            finish()
        }
        bt_read.setOnClickListener {
            if (TextUtils.isEmpty(et_backlog_title.text.toString())) {
                Toast.makeText(this, "请输入通知标题", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_backlog_content.text.toString())) {
                Toast.makeText(this, "请输入通知内容", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val param = HashMap<String, String>()
            param["notice_title"] = et_backlog_title.text.toString()
            param["notice_content"] = et_backlog_content.text.toString()
            param["author"] = CommonUtil.currentUser.user_id
            param["type"] = "1"
            OkHttpManager.instance?.post(RequestUrl.sendNotice, param, object : MyCallback {
                override fun onSuccess(json: String) {
                    Log.d("http_test", "success: $json")
                    Toast.makeText(this@SendNoticeActivity, "发布成功", Toast.LENGTH_LONG).show()
                    bt_read.isClickable = false
                    bt_read.setBackgroundResource(R.drawable.bg_button_no_click)
                    et_backlog_content.isEnabled = false
                    et_backlog_title.isEnabled = false
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