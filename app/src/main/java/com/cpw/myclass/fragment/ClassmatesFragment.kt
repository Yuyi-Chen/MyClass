package com.cpw.myclass.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpw.myclass.R
import com.cpw.myclass.activity.ClassmateActivity
import com.cpw.myclass.adapter.ClassmatesAdapter
import com.cpw.myclass.data.UserBean
import com.cpw.myclass.http.MyCallback
import com.cpw.myclass.http.OkHttpManager
import com.cpw.myclass.http.RequestUrl
import com.cpw.myclass.util.CommonUtil
import com.cpw.myclass.view.SideBar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_classmates.view.*
import net.sourceforge.pinyin4j.PinyinHelper
import okhttp3.Request
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClassmatesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClassmatesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mView: View
    private var classmatesList = arrayListOf<UserBean>()
    private val positions = HashMap<Char, Int>()
    private val adapter = ClassmatesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_classmates, container, false)
        mView.mSideBar.setTextView(mView.tv_show_side)
        val param = java.util.HashMap<String, String>()
        if (CommonUtil.currentUser != null) {
            param["class_id"] = CommonUtil.currentUser.class_id
        }
        OkHttpManager.instance?.post(RequestUrl.userList, param, object : MyCallback {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onSuccess(json: String) {
                val gson = Gson()
                val type = object : TypeToken<ArrayList<UserBean>>(){}.type
                val result: ArrayList<UserBean> = gson.fromJson(json, type)
                result.removeIf {
                    it.user_id == CommonUtil.currentUser.user_id
                }
                classmatesList = result
                resetData(classmatesList)
                adapter.setClassmates(classmatesList)
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
        adapter.setClickListener(object : ClassmatesAdapter.OnClassmateItemClickListener{
            override fun onClick(classmate: UserBean) {
                val intent = Intent(activity, ClassmateActivity::class.java)
                intent.putExtra("classmate", classmate)
                startActivity(intent)
            }
        })
        mView.mSideBar.setOnTouchingLetterChangedListener(object : SideBar.OnTouchingLetterChangedListener{
            override fun onTouchingLetterChanged(s: String?) {
                positions[s?.get(0)]?.let { mView.rv_classmates_list.scrollToPosition(it) }
            }
        })
        mView.rv_classmates_list.layoutManager = LinearLayoutManager(activity)
        mView.rv_classmates_list.adapter = adapter
        adapter.notifyDataSetChanged()
        return mView
    }

    private fun resetData(data: List<UserBean>) {
        for (i in data.indices) {
            val pinyinArray = PinyinHelper.toHanyuPinyinStringArray(data[i].user_name[0])
            if (pinyinArray != null) {
                val s = pinyinArray[0].substring(0, 1).toUpperCase()
                data[i].firstLetter = s[0]
                if (TextUtils.isEmpty(s)) {
                    data[i].firstLetter = '#'
                }
            }
        }
        classmatesList.sortBy {
            it.firstLetter
        }
        for ((i, item) in data.withIndex()) {
            if (!positions.containsKey(item.firstLetter)) {
                positions[item.firstLetter] = i
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClassmatesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClassmatesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}