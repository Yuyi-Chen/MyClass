package com.cpw.myclass.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.TestLooperManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpw.myclass.R
import com.cpw.myclass.adapter.NewsAdapter
import com.cpw.myclass.data.MessageBean
import com.cpw.myclass.data.MessageFragmentBean
import com.cpw.myclass.data.NewsBean
import com.cpw.myclass.http.MyCallback
import com.cpw.myclass.http.OkHttpManager
import com.cpw.myclass.http.RequestUrl
import com.cpw.myclass.util.CommonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_news.iv_back
import kotlinx.android.synthetic.main.activity_news.tv_fragment_title
import kotlinx.android.synthetic.main.activity_send_notice.*
import okhttp3.Request
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
    var news = arrayListOf<NewsBean>()
    val formatter = SimpleDateFormat("HH:mm")
    val refreshMessage = Timer()
    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            news = msg.obj as ArrayList<NewsBean>
            adapter.data = news
            adapter.notifyDataSetChanged()
            rv_news.scrollToPosition(news.size - 1)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val msg = intent.getSerializableExtra("msg") as MessageFragmentBean
        tv_fragment_title.text = msg.from_name
        adapter = NewsAdapter()
        rv_news.layoutManager = LinearLayoutManager(this)
        adapter.data = news
        rv_news.adapter = adapter
        iv_back.setOnClickListener {
            finish()
        }
        refreshMessage.schedule(object : TimerTask() {
            override fun run() {
                val list = ArrayList<NewsBean>()
                val param1 = HashMap<String, String>()
                param1["uid_1"] = msg.from_id
                param1["uid_2"] = CommonUtil.currentUser.user_id
                OkHttpManager.instance?.post(RequestUrl.messageList, param1, object : MyCallback {
                    override fun onSuccess(json: String) {
                        val gson = Gson()
                        val type = object : TypeToken<List<MessageBean>>(){}.type
                        val result = gson.fromJson<List<MessageBean>>(json, type)
                        for (message in result) {
                            val messageBean = NewsBean()
                            messageBean.message = message.message
                            messageBean.time = message.time.replace("T", " ")
                            if (message.from == msg.from_id) {
                                messageBean.type = 0
                                if (message.read == "0") {
                                    val param = HashMap<String, String>()
                                    param["message_id"] = message.id
                                    OkHttpManager.instance?.post(RequestUrl.readMessage, param, object : MyCallback {
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
                                }
                            } else {
                                messageBean.type = 1
                            }
                            list.add(messageBean)
                        }
                        if (list.size > news.size) {
                            val handlerMsg = Message()
                            handlerMsg.obj = list
                            handlerMsg.what = 1
                            handler.sendMessage(handlerMsg)
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
        }, 0, 10000)
        bt_send.setOnClickListener {
            if (TextUtils.isEmpty(et_news.text.toString())) {
                return@setOnClickListener
            }
            val param = HashMap<String, String>()
            param["message_to"] = msg.from_id
            param["message_from"] = CommonUtil.currentUser.user_id
            param["message_content"] = et_news.text.toString()
            et_news.setText("")
            rv_news.scrollToPosition(news.size - 1)
            OkHttpManager.instance?.post(RequestUrl.sendMessage, param, object : MyCallback {
                override fun onSuccess(json: String) {
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