package com.planningdevgmail.kyutechapp2018.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.planningdevgmail.kyutechapp2018.R
import org.jetbrains.anko.*

class SchoolBusFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val ui: SchoolBusFragmentUi = SchoolBusFragmentUi()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return ui.createView(AnkoContext.create(context, this)) // Ankoに変更
//        return inflater.inflate(R.layout.fragment_school_bus, container, false)
    }

    // Ui部分
    private class SchoolBusFragmentUi: AnkoComponent<SchoolBusFragment> {
        override fun createView(ui: AnkoContext<SchoolBusFragment>): View  = ui.run {
            linearLayout {
                layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
                scrollView {
                    verticalLayout {
                        // Bus情報の画像を入れていく
                        imageView(R.mipmap.schoolbus_time_schedule2019) {
                            adjustViewBounds = true
                        }.lparams(matchParent, wrapContent)

                        imageView(R.mipmap.schoolbus_schedule2019_30) {
                            adjustViewBounds = true
                        }.lparams(matchParent, wrapContent)

                    }.lparams(wrapContent, wrapContent)
                }.lparams(matchParent, wrapContent)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                SchoolBusFragment().apply {
                }
    }
}
