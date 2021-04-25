package com.cpw.myclass.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TestLooperManager
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpw.myclass.R
import com.cpw.myclass.adapter.NewsAdapter
import com.cpw.myclass.data.NewsBean
import kotlinx.android.synthetic.main.activity_news.*
import java.text.SimpleDateFormat
import java.util.*

class NewsActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
    val news = arrayListOf<NewsBean>()
    val formatter = SimpleDateFormat("HH:mm")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        tv_fragment_title.text = intent.getStringExtra("name")
        adapter = NewsAdapter()
        rv_news.layoutManager = LinearLayoutManager(this)
        rv_news.adapter = adapter
        bt_send.setOnClickListener {
            if (TextUtils.isEmpty(et_news.text.toString())) {
                return@setOnClickListener
            }
            val message = et_news.text.toString()
            et_news.setText("")
            val send = NewsBean()
            send.type = 1
            send.message = message
            send.time = formatter.format(Date())
            val receive = NewsBean()
            receive.time = formatter.format(Date())
            receive.message = "收到收到！"
            receive.type = 0
            news.add(send)
            news.add(receive)
            adapter.setNews(news)
            adapter.notifyDataSetChanged()
            rv_news.scrollToPosition(news.size - 1)
        }
    }
}