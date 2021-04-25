package com.cpw.myclass.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cpw.myclass.R
import com.cpw.myclass.data.ClassmatesBean
import com.cpw.myclass.data.ClassmatesType
import kotlinx.android.synthetic.main.activity_classmate.*

class ClassmateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classmate)
        val classmate = intent.getSerializableExtra("classmate") as ClassmatesBean
        tv_classmates_type.text = when(classmate.type) {
            ClassmatesType.ClassTeacher -> "班主任"
            ClassmatesType.LeagueBranchSecretary -> "团支部书记"
            ClassmatesType.Monitor -> "班长"
            ClassmatesType.Schoolmate -> "同学"
            ClassmatesType.SportsMonitor -> "文体委员"
            ClassmatesType.StudyMonitor -> "学习委员"
            ClassmatesType.ViceMonitor -> "副班长"
        }
        tv_classmates_name.text = classmate.name
        tv_phone_number.text = "电话：${classmate.phone_number}"
        tv_student_number.text = "学号：${classmate.student_number}"
        iv_back.setOnClickListener {
            finish()
        }
        ll_send_message.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            intent.putExtra("name", classmate.name)
            startActivity(intent)
        }
        ll_call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            val data: Uri = Uri.parse("tel:${classmate.phone_number}")
            intent.data = data
            startActivity(intent)
        }
    }
}