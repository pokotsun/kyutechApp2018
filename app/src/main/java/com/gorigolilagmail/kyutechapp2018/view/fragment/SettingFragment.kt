package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import com.gorigolilagmail.kyutechapp2018.R
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {
    private val settingItems =
            arrayOf("ユーザー情報更新", "P&Dについて", "このアプリについて", "ご不満・ご要望フォーム",
            "九工大飯塚キャンパスHP", "九工大シラバス", "九工大moodle", "九工大シラバス")
    

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
                0 -> {
                    val dialog = UserEditFragment.newInstance()
                    dialog.setTargetFragment(this, RESULT_CODE)
                    dialog.show(fragmentManager, "fragment_dialog")
                }
                1 -> {

                }
                2 -> {

                }
            }
        }
    }

    companion object {

        private const val RESULT_CODE: Int = 1000

        @JvmStatic
        fun newInstance() =
                SettingFragment()
    }
}
