package com.cpw.myclass.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cpw.myclass.R
import com.cpw.myclass.activity.NewsActivity
import com.cpw.myclass.adapter.MyItemRecyclerViewAdapter
import com.cpw.myclass.adapter.NewsFragmentAdapter
import com.cpw.myclass.data.MessageFragmentBean
import com.cpw.myclass.dummy.DummyContent
import com.cpw.myclass.util.CommonUtil
import kotlinx.android.synthetic.main.fragment_news.view.*

/**
 * A fragment representing a list of Items.
 */
class NewsFragment : Fragment() {

    private var columnCount = 1
    val adapter = NewsFragmentAdapter()
    var messageList = ArrayList<MessageFragmentBean>()
    private  var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        mView = view
        adapter.setNewsListener(object : NewsFragmentAdapter.OnNewsItemClickListener{
            override fun onClick(msg: MessageFragmentBean) {
                val intent = Intent(activity, NewsActivity::class.java)
                intent.putExtra("msg", msg)
                startActivity(intent)
            }
        })
        view.rv_news_list.layoutManager = LinearLayoutManager(activity)
        adapter.setMessage(messageList)
        view.rv_news_list.adapter  = adapter
        adapter.notifyDataSetChanged()
        return view
    }

    fun setMessage(list: ArrayList<MessageFragmentBean>) {
        messageList = list
        adapter.setMessage(messageList)
        if (mView != null) {
            mView!!.rv_news_list.adapter = adapter
        }
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        fun newInstance(columnCount: Int) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}