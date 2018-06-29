package com.planningdevgmail.kyutechapp2018.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.planningdevgmail.kyutechapp2018.R
import com.planningdevgmail.kyutechapp2018.view.customView.CircularTextView

/**
 * Created by pokotsun on 18/03/10.
 */


class TestFragment : Fragment() {

    companion object {
        fun newInstance(page: Int): TestFragment {
            val args: Bundle = Bundle().apply {
                putInt("page", page)
            }
            return TestFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val page = arguments.getInt("page", 0)
        val view = inflater!!.inflate(R.layout.fragment_test, container, false)
        view.findViewById<TextView>(R.id.page_text).text = "Page$page"

        view.findViewById<CircularTextView>(R.id.circularTextView).text = "${page+1000}"
        view.findViewById<CircularTextView>(R.id.circularTextView).setCircleStrokeColor(ContextCompat.getColor(context, R.color.newsTopic1))
        view.findViewById<CircularTextView>(R.id.circularTextView).setCircleBackgroundColor(ContextCompat.getColor(context, R.color.newsTopic2))

        return view
    }

}