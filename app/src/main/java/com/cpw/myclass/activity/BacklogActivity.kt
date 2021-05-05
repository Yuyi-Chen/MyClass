package com.cpw.myclass.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cpw.myclass.R
import com.cpw.myclass.data.ClassmatesType
import com.cpw.myclass.data.NoticeBean
import com.cpw.myclass.data.UserBean
import com.cpw.myclass.http.MyCallback
import com.cpw.myclass.http.OkHttpManager
import com.cpw.myclass.http.RequestUrl
import com.cpw.myclass.util.CommonUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_backlog.*
import okhttp3.Request
import java.io.IOException

class BacklogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backlog)
        val backlog = intent.getSerializableExtra("backlog") as NoticeBean
        val read = backlog.notice_read.split(",")
        val param = HashMap<String, String>()
        param["user_id"] = backlog.author
        OkHttpManager.instance?.post(RequestUrl.getUserMessage, param, object : MyCallback {
            override fun onSuccess(json: String) {
                Log.d("http_test", "success: $json")
                val gson = Gson()
                val result = gson.fromJson<UserBean>(json, UserBean::class.java)
                tv_classmates_name.text = result.user_name
                tv_classmates_type.text = ClassmatesType.getRoleString(result.user_role)
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
        tv_backlog_content.text = backlog.content
        tv_backlog_time.text = backlog.create_time + "发布"
        tv_backlog_title.text = backlog.title
        tv_read_number.text = "${read.size}人已读"
        if (read.contains(CommonUtil.currentUser.user_id)) {
            bt_read.text = "已确认"
            bt_read.setBackgroundResource(R.drawable.bg_button_no_click)
        } else {
            bt_read.text = "确认通知"
            bt_read.setBackgroundResource(R.drawable.bg_login_button)
        }

        bt_read.setOnClickListener {
            val param = HashMap<String, String>()
            param["user_id"] = CommonUtil.currentUser.user_id
            param["notice_id"] = backlog.notice_id
            OkHttpManager.instance?.post(RequestUrl.readNotice, param, object : MyCallback {
                override fun onSuccess(json: String) {
                    bt_read.text = "已确认"
                    bt_read.setBackgroundResource(R.drawable.bg_button_no_click)
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
        iv_back.setOnClickListener {
            finish()
        }
    }
}