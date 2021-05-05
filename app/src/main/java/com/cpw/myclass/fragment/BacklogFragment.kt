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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpw.myclass.R
import com.cpw.myclass.activity.BacklogActivity
import com.cpw.myclass.activity.SendNoticeActivity
import com.cpw.myclass.adapter.BacklogAdapter
import com.cpw.myclass.data.NoticeBean
import com.cpw.myclass.util.CommonUtil
import kotlinx.android.synthetic.main.fragment_backlog.view.*
import kotlin.collections.ArrayList

class BacklogFragment : Fragment() {
    private var noticeList = ArrayList<NoticeBean>()
    private val adapter = BacklogAdapter()
    private lateinit var mView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_backlog, container, false)
        mView = view
        view.rv_backlog_list.layoutManager = LinearLayoutManager(activity)
        adapter.setClickListener(object : BacklogAdapter.OnBacklogItemClickListener{
            override fun onClick(notice: NoticeBean) {
                val intent = Intent(activity, BacklogActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("backlog", notice)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        })
        view.rv_backlog_list.adapter = adapter
        view.iv_add.setOnClickListener {
            if (CommonUtil.currentUser.user_role == 6) {
                Toast.makeText(activity, "您没有此权限", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val mDialog = activity?.let { it1 -> Dialog(it1, R.style.BottomDialog) }
            val root = LayoutInflater.from(activity).inflate(R.layout.bottom_menu_more_item, null) as ConstraintLayout
            root.findViewById<TextView>(R.id.tv_menu_content).visibility = View.GONE
            root.findViewById<Button>(R.id.bt_1).let {
                it.text = "发布通知"
                it.setOnClickListener {
                    startActivity(Intent(activity, SendNoticeActivity::class.java))
                    mDialog?.dismiss()
                }
            }
            root.findViewById<Button>(R.id.bt_2).let {
                it.text = "发布投票"
                it.setOnClickListener {
                    startActivity(Intent(activity, SendNoticeActivity::class.java))
                    mDialog?.dismiss()
                }
            }
            root.findViewById<Button>(R.id.bt_3).let {
                it.text = "发布作业"
                it.setOnClickListener {
                    startActivity(Intent(activity, SendNoticeActivity::class.java))
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
        adapter.setNotice(noticeList)
        adapter.notifyDataSetChanged()
        return view
    }

    fun setNotice(list: ArrayList<NoticeBean>) {
        noticeList = list
        adapter.setNotice(noticeList)
        mView.rv_backlog_list.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() =
            BacklogFragment()
    }
}