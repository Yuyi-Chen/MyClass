package com.cpw.myclass.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cpw.myclass.R
import com.cpw.myclass.data.ClassmatesType
import com.cpw.myclass.data.MessageFragmentBean
import com.cpw.myclass.data.UserBean
import com.cpw.myclass.util.CommonUtil
import kotlinx.android.synthetic.main.activity_classmate.*

class ClassmateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classmate)
        val classmate = intent.getSerializableExtra("classmate") as UserBean
        tv_classmates_type.text = when(ClassmatesType.getClassmatesType(classmate.user_role)) {
            ClassmatesType.ClassTeacher -> "班主任"
            ClassmatesType.LeagueBranchSecretary -> "团支部书记"
            ClassmatesType.Monitor -> "班长"
            ClassmatesType.SportsMonitor -> "文体委员"
            ClassmatesType.StudyMonitor -> "学习委员"
            ClassmatesType.ViceMonitor -> "副班长"
            else -> "同学"
        }
        tv_classmates_name.text = classmate.user_name
        tv_phone_number.text = "电话：${classmate.user_phone}"
        tv_student_number.text = "学号：${classmate.user_id}"
        iv_back.setOnClickListener {
            finish()
        }
        ll_send_message.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            val msg = MessageFragmentBean()
            msg.from_id = classmate.user_id
            msg.no_read = "0"
            msg.message = ""
            msg.time = ""
            msg.from_role = classmate.user_role
            msg.from_name = classmate.user_name
            if (!CommonUtil.chatWithMe.contains(classmate.user_id)) {
                CommonUtil.chatWithMe.add(classmate.user_id)
            }
            intent.putExtra("msg", msg)
            startActivity(intent)
        }
        ll_call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            val data: Uri = Uri.parse("tel:${classmate.user_phone}")
            intent.data = data
            startActivity(intent)
        }
    }
}