package com.cpw.myclass.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cpw.myclass.R
import com.cpw.myclass.data.ClassmatesBean

class NewsFragmentAdapter() : RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder>() {
    var listener: OnNewsItemClickListener? = null
    var item = 10

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
        holder.delete.setOnClickListener {
            item--
            notifyDataSetChanged()
        }
        if (listener != null) {
            holder.item.setOnClickListener {
                listener!!.onClick("杨明")
            }
        }
    }

    override fun getItemCount(): Int = item

    fun setNewsListener(listener: OnNewsItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item = view.findViewById<ConstraintLayout>(R.id.item_view)
        val newFromView: TextView = view.findViewById(R.id.tv_classmates_name)
        val fromTypeView: TextView = view.findViewById(R.id.tv_classmates_type)
        val newsTimeView: TextView = view.findViewById(R.id.tv_news_time)
        val newsContentView: TextView = view.findViewById(R.id.tv_news_content)
        val newsNumView: TextView = view.findViewById(R.id.tv_news_num)
        val delete: Button = view.findViewById(R.id.btnDelete)
    }

    interface OnNewsItemClickListener{
        fun onClick(name: String)
    }
}