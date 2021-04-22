package com.cpw.myclass.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cpw.myclass.R

class BacklogAdapter() : RecyclerView.Adapter<BacklogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_backlog, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.backlogTitleView.text = "通知"
        holder.backlogContentView.text = "我快写完了啊啊啊啊啊 啊啊"
        holder.backlogTimeView.text = "2021年4月22日 16:56 发布"
        holder.isReadView.text = "未阅读"
    }

    override fun getItemCount(): Int = 10

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val backlogTitleView: TextView = view.findViewById(R.id.tv_notice_title)
        val backlogContentView: TextView = view.findViewById(R.id.tv_notice_content)
        val backlogTimeView: TextView = view.findViewById(R.id.tv_backlog_time)
        val isReadView: TextView = view.findViewById(R.id.tv_is_read)
    }
}