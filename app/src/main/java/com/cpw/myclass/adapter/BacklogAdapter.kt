package com.cpw.myclass.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cpw.myclass.R
import com.cpw.myclass.data.NoticeBean
import com.cpw.myclass.util.CommonUtil

class BacklogAdapter() : RecyclerView.Adapter<BacklogAdapter.ViewHolder>() {
    private var listener: OnBacklogItemClickListener? = null
    private var data: ArrayList<NoticeBean> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_backlog, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.backlogTitleView.text = data[position].title
        holder.backlogContentView.text = data[position].content
        holder.backlogTimeView.text = data[position].create_time
        val read = data[position].notice_read.split(",")
        if (read.contains(CommonUtil.currentUser.user_id)) {
            holder.isReadView.text = "已阅读"
            holder.isReadView.setBackgroundResource(R.drawable.bg_read)
        } else {
            holder.isReadView.text = "未阅读"
            holder.isReadView.setBackgroundResource(R.drawable.bg_not_read)
        }
        if (listener != null) {
            holder.item.setOnClickListener {
                listener!!.onClick(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun setClickListener(listener: OnBacklogItemClickListener) {
        this.listener = listener
    }

    fun setNotice(list: ArrayList<NoticeBean>) {
        data = list
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item = view
        val backlogTitleView: TextView = view.findViewById(R.id.tv_notice_title)
        val backlogContentView: TextView = view.findViewById(R.id.tv_notice_content)
        val backlogTimeView: TextView = view.findViewById(R.id.tv_backlog_time)
        val isReadView: TextView = view.findViewById(R.id.tv_is_read)
    }

    interface OnBacklogItemClickListener {
        fun onClick(notice: NoticeBean)
    }
}