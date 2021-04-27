package com.cpw.myclass.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpw.myclass.R
import com.cpw.myclass.activity.BacklogActivity
import com.cpw.myclass.adapter.BacklogAdapter
import com.cpw.myclass.data.BacklogBean
import kotlinx.android.synthetic.main.fragment_backlog.view.*

class BacklogFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_backlog, container, false)
        view.rv_backlog_list.layoutManager = LinearLayoutManager(activity)
        val adapter = BacklogAdapter()
        adapter.setClickListener(object : BacklogAdapter.OnBacklogItemClickListener{
            override fun onClick() {
                val backlog = BacklogBean()
                backlog.content = "各学院和2021届毕业生：\n" +
                        "    第六期《SIYB创业基础》课程即将开课，培训课程共10天（60学时），本期共招收60人（按报名顺序选课，选满即止，满60人开班），仅限2021届毕业生（本硕博均可）参加，培训合格将认定2个创新创业学分，并颁发由人社部和国际劳工组织联合冠名的创业培训合格证，报名截止时间4月15日。"
                backlog.initiator = "杨明"
                backlog.initiator_type = "班长"
                backlog.release_time = "2021年4月24日 15:06"
                backlog.title = "关于开设第六期《SIYB创业培训》课程的通知"
                val intent = Intent(activity, BacklogActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("backlog", backlog)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        })
        view.rv_backlog_list.adapter = adapter
        view.iv_add.setOnClickListener {
            val mDialog = activity?.let { it1 -> Dialog(it1, R.style.BottomDialog) }
            val root = LayoutInflater.from(activity).inflate(R.layout.bottom_menu_more_item, null) as ConstraintLayout
            root.findViewById<TextView>(R.id.tv_menu_content).visibility = View.GONE
            root.findViewById<Button>(R.id.bt_1).let {
                it.text = "发布通知"
                it.setOnClickListener {

                }
            }
            root.findViewById<Button>(R.id.bt_2).let {
                it.text = "发布投票"
                it.setOnClickListener {
                }
            }
            root.findViewById<Button>(R.id.bt_3).let {
                it.text = "发布作业"
                it.setOnClickListener {

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
        adapter.notifyDataSetChanged()
        return view
    }

    companion object {
        fun newInstance() =
            BacklogFragment()
    }
}