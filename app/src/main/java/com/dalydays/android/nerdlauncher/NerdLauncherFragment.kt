package com.dalydays.android.nerdlauncher

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_nerd_launcher.*
import java.util.*

class NerdLauncherFragment : Fragment() {
    private var mRecyclerView: RecyclerView? = null

    companion object {
        private val TAG = "NerdLauncherFragment"

        fun newInstance(): NerdLauncherFragment {
            return NerdLauncherFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_nerd_launcher, container, false)
        mRecyclerView = v.findViewById(R.id.app_recycler_view)
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)

        setupAdapter()
        return v
    }

    private fun setupAdapter() {
        val startupIntent = Intent(Intent.ACTION_MAIN)
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val packageManager = activity!!.packageManager
        val activities = packageManager.queryIntentActivities(startupIntent, 0)

        Collections.sort(activities, object: Comparator < ResolveInfo > {
            override fun compare(a: ResolveInfo?, b: ResolveInfo?): Int {
                val packageManager = activity!!.packageManager
                return String.CASE_INSENSITIVE_ORDER.compare(
                        a!!.loadLabel(packageManager).toString(),
                        b!!.loadLabel(packageManager).toString())
            }
        })

        Log.i(TAG, "Found " + activities.size + " activities.")
    }

    private class ActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var mResolveInfo: ResolveInfo? = null
        private val mNameTextView: TextView

        init {
            mNameTextView = itemView as TextView
        }

        fun bindActivity(resolveInfo: ResolveInfo) {
            mResolveInfo = resolveInfo
            // TODO: figure out how to get the activity here
            //val packageManager = activity!!.packageManager
        }
    }
}