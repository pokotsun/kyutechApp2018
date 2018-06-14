package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gorigolilagmail.kyutechapp2018.R

class SchoolBusFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_school_bus, container, false)
    }


    companion object {

        @JvmStatic
        fun newInstance() =
                SchoolBusFragment().apply {
                }
    }
}
