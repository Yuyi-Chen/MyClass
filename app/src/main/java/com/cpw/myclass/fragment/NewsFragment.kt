package com.cpw.myclass.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cpw.myclass.R
import com.cpw.myclass.adapter.MyItemRecyclerViewAdapter
import com.cpw.myclass.adapter.NewsFragmentAdapter
import com.cpw.myclass.dummy.DummyContent
import kotlinx.android.synthetic.main.fragment_news.view.*

/**
 * A fragment representing a list of Items.
 */
class NewsFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        val adapter = NewsFragmentAdapter()
        view.rv_news_list.layoutManager = LinearLayoutManager(activity)
        view.rv_news_list.adapter  = adapter
        adapter.notifyDataSetChanged()
        return view
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