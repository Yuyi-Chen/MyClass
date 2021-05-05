package com.cpw.myclass.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.cpw.myclass.R
import com.cpw.myclass.data.*
import com.cpw.myclass.fragment.BacklogFragment
import com.cpw.myclass.fragment.ClassmatesFragment
import com.cpw.myclass.fragment.MeFragment
import com.cpw.myclass.fragment.NewsFragment
import com.cpw.myclass.http.MyCallback
import com.cpw.myclass.http.OkHttpManager
import com.cpw.myclass.http.RequestUrl
import com.cpw.myclass.util.CommonUtil
import com.cpw.myclass.view.DragPointView
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_tab_content.view.*
import okhttp3.Request
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val mTabRes = arrayOf(R.drawable.ic_backlog_normal, R.drawable.ic_classmates_normal, R.drawable.ic_news_normal, R.drawable.ic_me_normal)
    private val mTabResPressed = arrayOf(R.drawable.ic_backlog_pressed, R.drawable.ic_classmates_pressed, R.drawable.ic_news_pressed, R.drawable.ic_me_pressed)
    private val mTabTitle = arrayOf("待办", "同学", "消息", "我的")
    private val mFragments = arrayOf(BacklogFragment.newInstance(), ClassmatesFragment.newInstance("", ""), NewsFragment.newInstance(1), MeFragment.newInstance("", ""))
    private val getBacklog = Timer()
    private val getMessage = Timer()
    private val getMessageOut = Timer()
    private lateinit var sp: SharedPreferences
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                val message = msg.obj as ArrayList<MessageFragmentBean>
                (mFragments[2] as NewsFragment).setMessage(message)
            } else if (msg.what == 1) {
                val notice = msg.obj as ArrayList<NoticeBean>
                (mFragments[0] as BacklogFragment).setNotice(notice)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        sp = this.getSharedPreferences("chat_with_me", Context.MODE_PRIVATE)
        val str = sp.getString("chat", "")
        val mGson = Gson()
        val mType = object : TypeToken<ArrayList<String>>(){}.type
        CommonUtil.chatWithMe = mGson.fromJson(str, mType)
        if (CommonUtil.chatWithMe == null) {
            CommonUtil.chatWithMe = ArrayList<String>()
        }
        if (CommonUtil.currentUser != null) {
            CommonUtil.chatWithMe.remove(CommonUtil.currentUser.user_id)
        }
        getBacklog.schedule(object : TimerTask() {
            override fun run() {
                val param = HashMap<String, String>()
                if (CommonUtil.currentUser != null) {
                    param["class_id"] = CommonUtil.currentUser.class_id
                }
                OkHttpManager.instance?.post(RequestUrl.noticeToday, param, object : MyCallback {
                    override fun onSuccess(json: String) {
                        val gson = Gson()
                        val result = gson.fromJson<ResultNoticeBean>(json, ResultNoticeBean::class.java)
                        for (notice in result.notice) {
                            notice.create_time = notice.create_time.replace("T", "  ")
                        }
                        val msg = Message()
                        msg.what = 1
                        msg.obj = result.notice
                        handler.sendMessage(msg)
                    }
                    override fun onBefore(request: Request) {
                        Log.d("http_test", "will start")
                    }
                    override fun onAfter() {
                        Log.d("http_test", "end")
                    }

                    override fun onFailed(e: IOException) {
                        Log.d("http_test", "failed: ${e.message}")
                    }
                })
            }
        }, 0, 60000)
        getMessage.schedule(object : TimerTask() {
            override fun run() {
                val param = HashMap<String, String>()
                if (CommonUtil.currentUser != null) {
                    param["user_id"] = CommonUtil.currentUser.user_id
                }
                OkHttpManager.instance?.post(RequestUrl.messageNotRead, param, object : MyCallback {
                    override fun onSuccess(json: String) {
                        val gson = Gson()
                        val type = object : TypeToken<ArrayList<MessageBean>>() {}.type
                        val result: ArrayList<MessageBean> = gson.fromJson(json, type)
                        for (message in result) {
                            if (!CommonUtil.chatWithMe.contains(message.from)) {
                                CommonUtil.chatWithMe.add(message.from)
                            }
                        }
                        val messageList = ArrayList<MessageFragmentBean>()
                        for (i in 0 until CommonUtil.chatWithMe.size) {
                            val param1 = HashMap<String, String>()
                            param1["user_from"] = CommonUtil.chatWithMe[i]
                            if (CommonUtil.currentUser != null) {
                                param1["user_to"] = CommonUtil.currentUser.user_id
                            }
                            OkHttpManager.instance?.post(RequestUrl.messageOut, param1, object : MyCallback {
                                override fun onSuccess(json: String) {
                                    val gson = Gson()
                                    val result = gson.fromJson<MessageFragmentBean>(json, MessageFragmentBean::class.java)
                                    messageList.add(result)
                                }
                                override fun onBefore(request: Request) {
                                    Log.d("http_test", "will start")
                                }
                                override fun onAfter() {
                                    Log.d("http_test", "end")
                                }

                                override fun onFailed(e: IOException) {
                                    Log.d("http_test", "failed: ${e.message}")
                                }
                            })
                        }
                        val msg = Message()
                        msg.what = 0
                        msg.obj = messageList
                        handler.sendMessageDelayed(msg, 5000)
                    }
                    override fun onBefore(request: Request) {
                        Log.d("http_test", "will start")
                    }
                    override fun onAfter() {
                        Log.d("http_test", "end")
                    }

                    override fun onFailed(e: IOException) {
                        Log.d("http_test", "failed: ${e.message}")
                    }
                })
            }
        }, 0, 10000)
//        getMessageOut.schedule(object : TimerTask() {
//            override fun run() {
//                val messageList = ArrayList<MessageFragmentBean>()
//                for (i in 0 until CommonUtil.chatWithMe.size) {
//                    val param1 = HashMap<String, String>()
//                    param1["user_from"] = CommonUtil.chatWithMe[i]
//                    param1["user_to"] = CommonUtil.currentUser.user_id
//                    OkHttpManager.instance?.post(RequestUrl.messageOut, param1, object : MyCallback {
//                        override fun onSuccess(json: String) {
//                            val gson = Gson()
//                            val result = gson.fromJson<MessageFragmentBean>(json, MessageFragmentBean::class.java)
//                            messageList.add(result)
//                        }
//                        override fun onBefore(request: Request) {
//                            Log.d("http_test", "will start")
//                        }
//                        override fun onAfter() {
//                            Log.d("http_test", "end")
//                        }
//
//                        override fun onFailed(e: IOException) {
//                            Log.d("http_test", "failed: ${e.message}")
//                        }
//                    })
//                }
//                val msg = Message()
//                msg.what = 0
//                msg.obj = messageList
//                handler.sendMessage(msg)
//            }
//        }, 2000, 10000)
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
                        } else {
                            icon?.setImageResource(mTabRes[i])
                            text?.setTextColor(resources.getColor(R.color.black))
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

    override fun onBackPressed() {
        val mDialog = Dialog(this, R.style.BottomDialog)
        val root = LayoutInflater.from(this).inflate(R.layout.bottom_menu, null) as ConstraintLayout
        root.findViewById<TextView>(R.id.tv_menu_content).text = "确定要退出软件吗？"
        root.findViewById<Button>(R.id.bt_yes).let {
            it.text = "退出"
            it.setOnClickListener {
                super.onBackPressed()
            }
        }
        root.findViewById<Button>(R.id.bt_no).let {
            it.text = "取消"
            it.setOnClickListener {
                mDialog.dismiss()
            }
        }
        mDialog.setContentView(root)
        val dialogWindow = mDialog.window
        dialogWindow?.setGravity(Gravity.BOTTOM)
        val lp = dialogWindow!!.attributes // 获取对话框当前的参数值
        lp.width = resources.displayMetrics.widthPixels // 宽度
        root.measure(0, 0)
        lp.height = root.measuredHeight
        lp.alpha = 9f // 透明度
        dialogWindow.attributes = lp
        mDialog.show()
    }

    override fun onResume() {
        super.onResume()
        LoginActivity.getUser(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        getBacklog.cancel()
        getMessage.cancel()
        getMessageOut.cancel()
        getMessage.cancel()
        val editor = sp.edit()
        editor.clear()
        editor.putString("chat", Gson().toJson(CommonUtil.chatWithMe))
        editor.commit()
    }

    private fun getTabView(context: Context, position: Int) : View {
        val view = LayoutInflater.from(context).inflate(R.layout.home_tab_content, null)
        view.tab_content_image.setImageResource(mTabRes[position])
        view.tab_content_text.text = mTabTitle[position]
        return view
    }
}