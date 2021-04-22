package com.cpw.myclass.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cpw.myclass.R

class NewsFragmentAdapter() : RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.newFromView.text = "杨明"
        holder.fromTypeView.text = "班长"
        holder.newsTimeView.text = "16:50"
        holder.newsContentView.text = "快点写不然毕不了业了"
        holder.newsNumView.text = "16"
    }

    override fun getItemCount(): Int = 10

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newFromView: TextView = view.findViewById(R.id.tv_classmates_name)
        val fromTypeView: TextView = view.findViewById(R.id.tv_classmates_type)
        val newsTimeView: TextView = view.findViewById(R.id.tv_news_time)
        val newsContentView: TextView = view.findViewById(R.id.tv_news_content)
        val newsNumView: TextView = view.findViewById(R.id.tv_news_num)
    }
}