package com.cpw.myclass.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.cpw.myclass.R
import com.cpw.myclass.activity.LoginActivity
import com.cpw.myclass.activity.MainActivity
import com.cpw.myclass.http.MyCallback
import com.cpw.myclass.http.OkHttpManager
import com.cpw.myclass.http.RequestUrl
import com.cpw.myclass.util.CommonUtil
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.fragment_me.view.*
import okhttp3.Request
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_me, container, false)
        view.tv_user_name.text = CommonUtil.currentUser.user_name
        view.tv_user_number.text = "学号：${CommonUtil.currentUser.user_id}"
        view.tv_user_phone.text = "电话：${CommonUtil.currentUser.user_phone}"
        if (CommonUtil.currentUser.user_role == 0) {
            view.rl_add_student.visibility = View.VISIBLE
        } else {
            view.rl_add_student.visibility = View.GONE
        }
        view.rl_quit.setOnClickListener {
            val mDialog = activity?.let { it1 -> Dialog(it1, R.style.BottomDialog) }
            val root = LayoutInflater.from(activity).inflate(R.layout.bottom_menu, null) as ConstraintLayout
            root.findViewById<TextView>(R.id.tv_menu_content).text = "确定要退出登录吗？"
            root.findViewById<Button>(R.id.bt_yes).let {
                it.text = "退出"
                it.setOnClickListener {
                    startActivity(Intent(activity, LoginActivity::class.java))
                    activity?.finish()
                }
            }
            root.findViewById<Button>(R.id.bt_no).let {
                it.text = "取消"
                it.setOnClickListener {
                    mDialog?.dismiss()
                }
            }
            mDialog?.setContentView(root)
            val dialogWindow = mDialog?.window
            dialogWindow?.setGravity(Gravity.BOTTOM)
            val lp = dialogWindow!!.attributes // 获取对话框当前的参数值
            lp.width = resources.displayMetrics.widthPixels // 宽度
            root.measure(0, 0)
            lp.height = root.measuredHeight
            lp.alpha = 9f // 透明度
            dialogWindow.attributes = lp
            mDialog.show()
        }
        view.rl_version.setOnClickListener {
            Toast.makeText(activity, "当前版本 V1.0.0", Toast.LENGTH_LONG).show()
        }
        view.rl_add_student.setOnClickListener {
            val mDialog = activity?.let { it1 -> Dialog(it1, R.style.BottomDialog) }
            val root = LayoutInflater.from(activity).inflate(R.layout.bottom_menu_more_item, null) as ConstraintLayout
            root.findViewById<TextView>(R.id.tv_menu_content).visibility = View.GONE
            root.findViewById<Button>(R.id.bt_1).let {
                it.text = "单个添加"
                it.setOnClickListener {
                    mDialog?.dismiss()
                    val addDialog = activity?.let { it1 -> Dialog(it1, R.style.BottomDialog) }
                    val layout = LayoutInflater.from(activity).inflate(R.layout.add_student_dialog, null) as ConstraintLayout
                    layout.findViewById<Button>(R.id.bt_add).setOnClickListener {
                        if (TextUtils.isEmpty(layout.findViewById<EditText>(R.id.et_user_name).text.toString())) {
                            Toast.makeText(activity, "请输入学生姓名", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        if (TextUtils.isEmpty(layout.findViewById<EditText>(R.id.et_user_id).text.toString())) {
                            Toast.makeText(activity, "请输入学生学号", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        if (TextUtils.isEmpty(layout.findViewById<EditText>(R.id.et_user_phone).text.toString())) {
                            Toast.makeText(activity, "请输入学生电话", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        val param = HashMap<String, String>()
                        param["user_name"] = layout.findViewById<EditText>(R.id.et_user_name).text.toString()
                        param["user_phone"] = layout.findViewById<EditText>(R.id.et_user_phone).text.toString()
                        param["user_id"] = layout.findViewById<EditText>(R.id.et_user_id).text.toString()
                        param["class_id"] = CommonUtil.currentUser.user_id
                        OkHttpManager.instance?.post(RequestUrl.addStudent, param, object :
                            MyCallback {
                            override fun onSuccess(json: String) {
                                addDialog?.dismiss()
                                Toast.makeText(activity, "添加成功", Toast.LENGTH_SHORT).show()
                            }

                            override fun onBefore(request: Request) {
                                Log.d("http_test", "will start")
                            }

                            override fun onAfter() {
                                Log.d("http_test", "end")
                            }

                            override fun onFailed(e: IOException) {
                                Log.d("http_test", "failed: ${e.message}")
                                addDialog?.dismiss()
                            }
                        })
                    }
                    addDialog?.setContentView(layout)
                    val dialogWindow = addDialog?.window
                    dialogWindow?.setGravity(Gravity.CENTER)
                    val lp = dialogWindow!!.attributes // 获取对话框当前的参数值
                    lp.width = resources.displayMetrics.widthPixels // 宽度
                    layout.measure(0, 0)
                    lp.height = layout.measuredHeight
                    lp.alpha = 9f // 透明度
                    dialogWindow.attributes = lp
                    addDialog.show()
                }
            }
            root.findViewById<Button>(R.id.bt_2).let {
                it.text = "批量添加"
                it.setOnClickListener {
                }
            }
            root.findViewById<Button>(R.id.bt_3).visibility = View.GONE
            mDialog?.setContentView(root)
            val dialogWindow = mDialog?.window
            dialogWindow?.setGravity(Gravity.BOTTOM)
            val lp = dialogWindow!!.attributes // 获取对话框当前的参数值
            lp.width = resources.displayMetrics.widthPixels // 宽度
            root.measure(0, 0)
            lp.height = root.measuredHeight
            lp.alpha = 9f // 透明度
            dialogWindow.attributes = lp
            mDialog.show()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}