package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.model.Syllabus
import com.gorigolilagmail.kyutechapp2018.view.adapter.SyllabusListAdapter
import kotlinx.android.synthetic.main.fragment_syllabus_list.*

class SyllabusListFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_syllabus_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SyllabusListAdapter(context)
        val syllabuses: List<Syllabus> = listOf(
                Syllabus.createDummy(),
                Syllabus.createDummy(),
                Syllabus.createDummy(),
                Syllabus.createDummy(),
                Syllabus.createDummy(),
                Syllabus.createDummy(),
                Syllabus.createDummy(),
                Syllabus.createDummy(),
                Syllabus.createDummy(),
                Syllabus.createDummy(),
                Syllabus.createDummy(),
                Syllabus.createDummy()
        )
        adapter.items = syllabuses
        syllabus_list.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SyllabusListFragment()
    }
}
