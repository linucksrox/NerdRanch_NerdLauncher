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
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_nerd_launcher.view.*
import kotlinx.android.synthetic.main.item_launchable.view.*
import java.util.*



class NerdLauncherFragment : Fragment() {

    companion object {
        private val TAG = "NerdLauncherFragment"

        fun newInstance(): NerdLauncherFragment {
            return NerdLauncherFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_nerd_launcher, container, false)
        v.app_recycler_view.layoutManager = LinearLayoutManager(activity)

        setupAdapter(v)
        return v
    }

    private fun setupAdapter(v: View) {
        val startupIntent = Intent(Intent.ACTION_MAIN)
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val packageManager = activity!!.packageManager
        val activities = packageManager.queryIntentActivities(startupIntent, 0)

        Collections.sort(activities, object: Comparator < ResolveInfo > {
            override fun compare(a: ResolveInfo, b: ResolveInfo): Int {
                val packageManager = activity!!.packageManager
                return String.CASE_INSENSITIVE_ORDER.compare(
                        a.loadLabel(packageManager).toString(),
                        b.loadLabel(packageManager).toString())
            }
        })

        Log.i(TAG, "Found " + activities.size + " activities.")

        v.app_recycler_view.adapter = ActivityAdapter(activities)
    }

    private class ActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var mResolveInfo: ResolveInfo

        fun bindActivity(resolveInfo: ResolveInfo) {
            mResolveInfo = resolveInfo
            val packageManager = itemView.context.packageManager
            val appName = mResolveInfo.loadLabel(packageManager).toString()
            val icon = mResolveInfo.loadIcon(packageManager)
            itemView.item_launcher_text.text = appName
            itemView.item_launcher_text.setOnClickListener(this)
            itemView.item_launcher_icon.setImageDrawable(icon)
        }

        override fun onClick(view: View) {
            val activityInfo = mResolveInfo.activityInfo

            val i = Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName,
                            activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            view.context.startActivity(i)
        }
    }

    private class ActivityAdapter(private val mActivities: List<ResolveInfo>) : RecyclerView.Adapter<ActivityHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
            var layoutInflater = LayoutInflater.from(parent.context)
            var view = layoutInflater.inflate(R.layout.item_launchable, parent, false)
            return ActivityHolder(view)
        }

        override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
            var resolveInfo = mActivities[position]
            holder.bindActivity(resolveInfo)
        }

        override fun getItemCount(): Int {
            return mActivities.size
        }
    }
}