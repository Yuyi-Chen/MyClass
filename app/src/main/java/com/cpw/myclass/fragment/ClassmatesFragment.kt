package com.cpw.myclass.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpw.myclass.R
import com.cpw.myclass.activity.ClassmateActivity
import com.cpw.myclass.adapter.ClassmatesAdapter
import com.cpw.myclass.data.ClassmatesBean
import com.cpw.myclass.data.ClassmatesType
import com.cpw.myclass.view.SideBar
import kotlinx.android.synthetic.main.fragment_classmates.view.*
import net.sourceforge.pinyin4j.PinyinHelper


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
    private val classmatesList = arrayListOf<ClassmatesBean>()
    private val positions = HashMap<Char, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        classmatesList.clear()
        classmatesList.add(ClassmatesBean("杨明", "12312341234", "20171001000", ClassmatesType.ViceMonitor))
        classmatesList.add(ClassmatesBean("阿杰", "12312341234", "20171001000", ClassmatesType.StudyMonitor))
        classmatesList.add(ClassmatesBean("不要命", "12312341234", "20171001000", ClassmatesType.SportsMonitor))
        classmatesList.add(ClassmatesBean("董少", "12312341234", "20171001000", ClassmatesType.Schoolmate))
        classmatesList.add(ClassmatesBean("董少刷", "12312341234", "20171001000", ClassmatesType.Monitor))
        classmatesList.add(ClassmatesBean("废物", "12312341234", "20171001000", ClassmatesType.LeagueBranchSecretary))
        classmatesList.add(ClassmatesBean("废弃物", "12312341234", "20171001000", ClassmatesType.ClassTeacher))
        classmatesList.add(ClassmatesBean("无翼鸟个", "12312341234", "20171001000", ClassmatesType.Schoolmate))
        classmatesList.add(ClassmatesBean("爱是两方面", "12331231234", "20171001000", ClassmatesType.Schoolmate))
        classmatesList.add(ClassmatesBean("拉拉刘", "1231234213", "20171001000", ClassmatesType.Schoolmate))
        classmatesList.add(ClassmatesBean("四方达麻烦", "12312341234", "20171001000", ClassmatesType.getClassmatesType(1)))
        classmatesList.add(ClassmatesBean("@看建安费拿到家", "12312341234", "20171001000", ClassmatesType.Schoolmate))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_classmates, container, false)
        mView.mSideBar.setTextView(mView.tv_show_side)
        resetData(classmatesList)
        val adapter = ClassmatesAdapter(classmatesList)
        adapter.setClickListener(object : ClassmatesAdapter.OnClassmateItemClickListener{
            override fun onClick(classmate: ClassmatesBean) {
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

    private fun resetData(data: List<ClassmatesBean>) {
        for (i in data.indices) {
            val pinyinArray = PinyinHelper.toHanyuPinyinStringArray(data[i].name[0])
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