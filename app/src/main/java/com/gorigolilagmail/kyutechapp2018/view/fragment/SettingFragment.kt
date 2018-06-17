package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast

import com.gorigolilagmail.kyutechapp2018.R
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {
    private val settingItems =
            arrayOf("ユーザー情報更新", "P&Dについて", "このアプリについて", "ご不満・ご要望フォーム",
                    "九工大飯塚キャンパスHP", "九工大シラバス", "九工大moodle")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting_list.adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, settingItems)

        setting_list.setOnItemClickListener { parent, view, position, id ->
            when(position) {
                USER_SETTING -> {
                    val dialog = UserEditFragment.newInstance()
                    dialog.setTargetFragment(this, RESULT_CODE)
                    dialog.show(fragmentManager, "fragment_dialog")
                }
                ABOUT_P_AND_D -> goToBrowser("https://www.planningdev.com/")
                ABOUT_THIS_APP -> {
                    // TODO 未実装
                    Toast.makeText(context, "申し訳ありません. 準備中です.", Toast.LENGTH_SHORT).show()
                }
                DEMAND_FORM -> {
                    // TODO 未実装
                    Toast.makeText(context, "申し訳ありません. 準備中です.", Toast.LENGTH_SHORT).show()
                }
                KYUTECH_CAMPUS_HP -> goToBrowser("https://www.iizuka.kyutech.ac.jp/")
                SYLLABUS_HP -> goToBrowser("https://edragon-syllabus.jimu.kyutech.ac.jp/guest/syllabuses")
                MOODLE_HP -> goToBrowser("https://ict-i.el.kyutech.ac.jp/")
            }
        }
    }

    private fun goToBrowser(url: String) {
        val uri = Uri.parse(url)
        Intent(Intent.ACTION_VIEW, uri).run { startActivity(this) }
    }

    companion object {
        private const val USER_SETTING: Int = 0
        private const val ABOUT_P_AND_D: Int = 1
        private const val ABOUT_THIS_APP: Int = 2
        private const val DEMAND_FORM: Int = 3
        private const val KYUTECH_CAMPUS_HP: Int = 4
        private const val SYLLABUS_HP: Int = 5
        private const val MOODLE_HP: Int = 6

        private const val RESULT_CODE: Int = 1000

        @JvmStatic
        fun newInstance() =
                SettingFragment()
    }
}
