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
    private val settingItems = arrayOf("設定", "P&Dとは", "シラバスにGo", "LiveCampusにGo")


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
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                SettingFragment()
    }
}
