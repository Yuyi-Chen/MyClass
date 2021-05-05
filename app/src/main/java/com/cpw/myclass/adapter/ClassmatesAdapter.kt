package com.cpw.myclass.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cpw.myclass.R
import com.cpw.myclass.data.ClassmatesType
import com.cpw.myclass.data.UserBean

class ClassmatesAdapter() : RecyclerView.Adapter<ClassmatesAdapter.ViewHolder>() {
    var listener: OnClassmateItemClickListener? = null
    private var data = ArrayList<UserBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_classmates, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        if(position != 0 && item.firstLetter == data[position - 1].firstLetter) {
            holder.codeViewContainer.visibility = View.GONE
        } else {
            holder.codeViewContainer.visibility = View.VISIBLE
        }
        holder.codeView.text = item.firstLetter.toString()
        holder.nameView.text = item.user_name
        holder.numberView.text = item.user_phone
        holder.typeView.text = when(ClassmatesType.getClassmatesType(item.user_role)) {
            ClassmatesType.ClassTeacher -> "班主任"
            ClassmatesType.LeagueBranchSecretary -> "团支部书记"
            ClassmatesType.Monitor -> "班长"
            ClassmatesType.SportsMonitor -> "文体委员"
            ClassmatesType.StudyMonitor -> "学习委员"
            ClassmatesType.ViceMonitor -> "副班长"
            else -> "同学"
        }
        if (listener != null) {
            holder.item.setOnClickListener {
                listener!!.onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun setClickListener(listener: OnClassmateItemClickListener) {
        this.listener = listener
    }

    fun setClassmates(list: ArrayList<UserBean>) {
        data = list
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item = view
        val codeView: TextView = view.findViewById(R.id.tv_code)
        val codeViewContainer: LinearLayout = view.findViewById(R.id.ll_code_container)
        val nameView: TextView = view.findViewById(R.id.tv_classmates_name)
        val numberView: TextView = view.findViewById(R.id.tv_classmates_phone_number)
        val typeView: TextView = view.findViewById(R.id.tv_classmates_type)
    }

    interface OnClassmateItemClickListener{
        fun onClick(classmate: UserBean)
    }
}