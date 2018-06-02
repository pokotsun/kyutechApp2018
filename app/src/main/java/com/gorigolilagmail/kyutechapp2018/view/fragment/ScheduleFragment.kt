package com.gorigolilagmail.kyutechapp2018.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast

import com.gorigolilagmail.kyutechapp2018.R
import com.gorigolilagmail.kyutechapp2018.view.customView.ClassGridItem
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onResume() {
        super.onResume()

        // クラスを全25コマ入れていく
        for(i in 0 until 5) {
            for( j in 0 until 5) {
                val item = ClassGridItem(context)
                val params: GridLayout.LayoutParams = GridLayout.LayoutParams()
                Log.d("i_test: $i", "${i % 5}, ${i % 5}")
                params.columnSpec = GridLayout.spec(i % 5, GridLayout.FILL, 1f)
                params.rowSpec = GridLayout.spec(j % 5, GridLayout.FILL, 1f)
                item.layoutParams = params
                item.setOnClickListener {
                    Toast.makeText(context, "($j, $i)のアイテムがタップされました", Toast.LENGTH_SHORT).show()
                }
                schedule_container.addView(item)
            }
        }

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
