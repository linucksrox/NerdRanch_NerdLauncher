package com.dalydays.android.nerdlauncher

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_nerd_launcher.*

class NerdLauncherFragment : Fragment() {
    private var mRecyclerView: RecyclerView? = null

    companion object {
        fun newInstance(): NerdLauncherFragment {
            return NerdLauncherFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_nerd_launcher, container, false)
        mRecyclerView = v.findViewById(R.id.app_recycler_view)
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)

        return v
    }
}