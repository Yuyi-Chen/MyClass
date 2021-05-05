package com.cpw.myclass.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cpw.myclass.R
import com.cpw.myclass.data.NewsBean
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    var data: List<NewsBean>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news_send_receive, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.news_time_container.visibility = View.GONE
        holder.receive_container.visibility = View.GONE
        holder.send_container.visibility = View.GONE
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val nowTime = formatter.parse(data?.get(position)?.time)
        val nowHour = nowTime.hours
        val nowMinutes = nowTime.minutes
        if (position == 0) {
            holder.news_time_container.visibility = View.VISIBLE
            holder.news_time.text = data?.get(position)?.time
        } else {
            val lastTime = formatter.parse(data?.get(position - 1)?.time)
            val lastHour = lastTime.hours
            val lastMinutes = lastTime.minutes
            if (lastHour != nowHour) {
                holder.news_time_container.visibility = View.VISIBLE
                holder.news_time.text = data?.get(position)?.time
            } else {
                if (nowMinutes - lastMinutes >= 2) {
                    holder.news_time_container.visibility = View.VISIBLE
                    holder.news_time.text = data?.get(position)?.time
                } else {
                    holder.news_time_container.visibility = View.GONE
                }
            }
        }
        if (data?.get(position)?.type == 0) {
            holder.receive_container.visibility = View.VISIBLE
            holder.send_container.visibility = View.GONE
            holder.new_receive.text = data?.get(position)?.message
        } else {
            holder.send_container.visibility = View.VISIBLE
            holder.receive_container.visibility = View.GONE
            holder.new_send.text = data?.get(position)?.message
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun setNews(data: List<NewsBean>) {
        this.data = data
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val news_time_container: LinearLayout = view.findViewById(R.id.ll_new_time_container)
        val news_time: TextView = view.findViewById(R.id.tv_new_time)
        val receive_container: RelativeLayout = view.findViewById(R.id.ll_new_receive_container)
        val new_receive: TextView = view.findViewById(R.id.tv_news_receive)
        val send_container: RelativeLayout = view.findViewById(R.id.ll_new_send_container)
        val new_send: TextView = view.findViewById(R.id.tv_news_send)
    }
}