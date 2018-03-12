package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gorigolilagmail.kyutechapp2018.R

class ScheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }


    companion object {
        fun newInstance(page: Int): ScheduleFragment {
            val args: Bundle = Bundle().apply {
                putInt("page", page)
            }
            return ScheduleFragment().apply {
                arguments = args
            }
        }
    }



}// Required empty public constructor
