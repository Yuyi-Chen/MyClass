package com.cpw.myclass.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.cpw.myclass.R
import com.cpw.myclass.fragment.BacklogFragment
import com.cpw.myclass.fragment.ClassmatesFragment
import com.cpw.myclass.fragment.MeFragment
import com.cpw.myclass.fragment.NewsFragment
import com.cpw.myclass.view.DragPointView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_tab_content.view.*

class MainActivity : AppCompatActivity() {

    private val mTabRes = arrayOf(R.drawable.ic_backlog_normal, R.drawable.ic_classmates_normal, R.drawable.ic_news_normal, R.drawable.ic_me_normal)
    private val mTabResPressed = arrayOf(R.drawable.ic_backlog_pressed, R.drawable.ic_classmates_pressed, R.drawable.ic_news_pressed, R.drawable.ic_me_pressed)
    private val mTabTitle = arrayOf("待办", "同学", "消息", "我的")
    private val mFragments = arrayOf(BacklogFragment.newInstance("", ""), ClassmatesFragment.newInstance("", ""), NewsFragment.newInstance(1), MeFragment.newInstance("", ""))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        bottom_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    onTabItemSelected(tab.position)
                    for (i in 0 until bottom_tab_layout.tabCount) {
                        val view = bottom_tab_layout.getTabAt(i)?.customView
                        val icon = view?.findViewById<ImageView>(R.id.tab_content_image)
                        val text = view?.findViewById<TextView>(R.id.tab_content_text)
                        val num = view?.findViewById<DragPointView>(R.id.tab_num)
                        if (i == tab.position) {
                            icon?.setImageResource(mTabResPressed[i])
                            text?.setTextColor(resources.getColor(R.color.color_main))
                            num?.visibility = View.VISIBLE
                        } else {
                            icon?.setImageResource(mTabRes[i])
                            text?.setTextColor(resources.getColor(R.color.black))
                            num?.visibility = View.INVISIBLE
                        }
                    }
                }
            }

        })

        for (i in 0 until 4) {
            bottom_tab_layout.addTab(bottom_tab_layout.newTab().setCustomView(getTabView(this, i)))
        }
    }

    private fun onTabItemSelected(position: Int) {
        mFragments[position]?.let {
            supportFragmentManager.beginTransaction().replace(R.id.home_container, it).commit()
        }
    }

    private fun getTabView(context: Context, position: Int) : View {
        val view = LayoutInflater.from(context).inflate(R.layout.home_tab_content, null)
        view.tab_content_image.setImageResource(mTabRes[position])
        view.tab_content_text.text = mTabTitle[position]
        return view
    }
}