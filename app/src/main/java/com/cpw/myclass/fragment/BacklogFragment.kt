package com.cpw.myclass.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpw.myclass.R
import com.cpw.myclass.activity.BacklogActivity
import com.cpw.myclass.adapter.BacklogAdapter
import com.cpw.myclass.data.BacklogBean
import kotlinx.android.synthetic.main.fragment_backlog.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BacklogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BacklogFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        adapter.notifyDataSetChanged()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BacklogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BacklogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}