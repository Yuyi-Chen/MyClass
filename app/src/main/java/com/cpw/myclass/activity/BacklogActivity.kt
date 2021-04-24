package com.cpw.myclass.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cpw.myclass.R
import com.cpw.myclass.data.BacklogBean
import kotlinx.android.synthetic.main.activity_backlog.*

class BacklogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backlog)
        val backlog = intent.getSerializableExtra("backlog") as BacklogBean
        tv_classmates_name.text = backlog.initiator
        tv_classmates_type.text = backlog.initiator_type
        tv_backlog_content.text = backlog.content
        tv_backlog_time.text = backlog.release_time + "发布"
        tv_backlog_title.text = backlog.title
        tv_read_number.text = "9人已读"
        iv_back.setOnClickListener {
            finish()
        }
    }
}