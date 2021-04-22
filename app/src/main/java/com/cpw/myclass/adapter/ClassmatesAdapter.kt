package com.cpw.myclass.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cpw.myclass.R
import com.cpw.myclass.data.ClassmatesBean
import com.cpw.myclass.data.ClassmatesType

class ClassmatesAdapter(private val values: List<ClassmatesBean>) : RecyclerView.Adapter<ClassmatesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_classmates, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if(position != 0 && item.firstLetter == values[position - 1].firstLetter) {
            holder.codeViewContainer.visibility = View.GONE
        } else {
            holder.codeViewContainer.visibility = View.VISIBLE
        }
        holder.codeView.text = item.firstLetter.toString()
        holder.nameView.text = item.name
        holder.numberView.text = item.phone_number
        holder.typeView.text = when(item.type) {
            ClassmatesType.ClassTeacher -> "班主任"
            ClassmatesType.LeagueBranchSecretary -> "团支部书记"
            ClassmatesType.Monitor -> "班长"
            ClassmatesType.Schoolmate -> "同学"
            ClassmatesType.SportsMonitor -> "文体委员"
            ClassmatesType.StudyMonitor -> "学习委员"
            ClassmatesType.ViceMonitor -> "副班长"
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val codeView: TextView = view.findViewById(R.id.tv_code)
        val codeViewContainer: LinearLayout = view.findViewById(R.id.ll_code_container)
        val nameView: TextView = view.findViewById(R.id.tv_classmates_name)
        val numberView: TextView = view.findViewById(R.id.tv_classmates_phone_number)
        val typeView: TextView = view.findViewById(R.id.tv_classmates_type)
    }
}