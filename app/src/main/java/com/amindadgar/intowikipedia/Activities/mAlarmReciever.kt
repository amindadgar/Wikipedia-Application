package com.amindadgar.intowikipedia.Activities

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.karn.notify.Notify

class mAlarmReciever:BroadcastReceiver(){


    override fun onReceive(context: Context, intent: Intent) {

        Notify.with(context)
                .content {

                    title = "Time to Explore"
                    text = "Click to dive into articles 😃"

            }.meta {

                val mainIntent = Intent(context,MainActivity::class.java)
                clickIntent = PendingIntent.getActivity(context,0,
                    mainIntent,0)

            }.show()
        }
}