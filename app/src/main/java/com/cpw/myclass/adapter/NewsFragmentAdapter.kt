package com.cpw.myclass.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cpw.myclass.R
import com.cpw.myclass.data.ClassmatesType
import com.cpw.myclass.data.MessageFragmentBean
import com.cpw.myclass.util.CommonUtil

class NewsFragmentAdapter() : RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder>() {
    var listener: OnNewsItemClickListener? = null
    private var data = ArrayList<MessageFragmentBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.newFromView.text = data[position].from_name
        holder.fromTypeView.text = ClassmatesType.getRoleString(data[position].from_role)
        if (data[position].time.split(" ").size >= 4) {
            var time = data[position].time
            var times = time.split(" ")
            time = times[4]
            times = time.split(":")
            holder.newsTimeView.text = "${times[0]}:${times[1]}"
        }
        holder.newsContentView.text = data[position].message
        if (data[position].no_read.toInt() == 0) {
            holder.newsNumView.visibility = View.INVISIBLE
        } else {
            holder.newsNumView.visibility = View.VISIBLE
            holder.newsNumView.text = data[position].no_read
        }
        holder.delete.setOnClickListener {
            CommonUtil.chatWithMe.remove(data[position].from_id)
            data.removeAt(position)
            notifyDataSetChanged()
        }
        if (listener != null) {
            holder.item.setOnClickListener {
                listener!!.onClick(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun setMessage(list: ArrayList<MessageFragmentBean>) {
        data = list
    }

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
        fun onClick(msg: MessageFragmentBean)
    }
}