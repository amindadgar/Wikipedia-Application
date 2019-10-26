package com.amindadgar.intowikipedia.Activities

import Fragments.ButtonNavigationFragments.Explorefragment
import Fragments.ButtonNavigationFragments.HistoryFragment
import Fragments.ButtonNavigationFragments.favouriteFragment
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import application.WikiApplication
import com.amindadgar.intowikipedia.R
import kotlinx.android.synthetic.main.activity_main.*
import managers.WikiManager
import java.util.*


class MainActivity : AppCompatActivity() {

    private val Explorefragment: Explorefragment
    private val historyfragment: HistoryFragment
    private val favouriteFragment: favouriteFragment
    private var WikiManager: WikiManager?=null
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    init {
        Explorefragment = Explorefragment()
        historyfragment = HistoryFragment()
        favouriteFragment = favouriteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container,Explorefragment)
        transaction.commit()



        WikiManager = (applicationContext as WikiApplication).wikiManager



        navigation.setNavigationChangeListener { view, _ ->
            changefragment(view)
        }

        navigation.setCurrentActiveItem(1)
//        setAlarm()
    }



    private fun setAlarm(){

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmintent = Intent(this, mAlarmReciever::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,100,
            alarmintent,PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 15)
        calendar.set(Calendar.MINUTE,0)

        val sharedPreferences = this.getSharedPreferences("LayoutMode",Context.MODE_PRIVATE)
        val AlarmTime = sharedPreferences.getString("notificationTime","24")

        when(AlarmTime){

            "1" -> {
                alarmManager.cancel(pendingIntent)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,AlarmManager.INTERVAL_HOUR,
                pendingIntent)

            }

            "3" ->{
                alarmManager.cancel(pendingIntent)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,AlarmManager.INTERVAL_DAY/8,
                    pendingIntent)
            }

            "6" ->{
                alarmManager.cancel(pendingIntent)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis, AlarmManager.INTERVAL_DAY/4,
                    pendingIntent)
            }

            "12" -> {
                alarmManager.cancel(pendingIntent)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,AlarmManager.INTERVAL_DAY/2,
                    pendingIntent)
            }

            "24" -> {
                alarmManager.cancel(pendingIntent)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis, AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
            }
            "48" -> {
                alarmManager.cancel(pendingIntent)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,AlarmManager.INTERVAL_DAY *2,
                    pendingIntent)
            }
            "72" -> {
                alarmManager.cancel(pendingIntent)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis, AlarmManager.INTERVAL_DAY*3,
                    pendingIntent)
            }
        }


    }


    private fun changefragment(view:View){

        val transaction = this.supportFragmentManager.beginTransaction()



        when(view.id){
            R.id.Favourite_Navigation -> {
                if (Explorefragment.inExplorefragment == true)
                    transaction.setCustomAnimations(R.anim.enter,R.anim.exit)
                else transaction.setCustomAnimations(R.anim.pop_exit,R.anim.pop_enter)
                transaction.replace(R.id.fragment_container,favouriteFragment)
                transaction.commit()
            }
            R.id.History_Navigation -> {
                transaction.setCustomAnimations(R.anim.enter,R.anim.exit)
                transaction.replace(R.id.fragment_container,historyfragment)
                transaction.commit()
            }
            R.id.Explore_Navigation-> {
                transaction.setCustomAnimations(R.anim.pop_exit,R.anim.pop_enter)
                transaction.replace(R.id.fragment_container,Explorefragment)
                transaction.commit()
            }
        }
    }

    fun gotosetting(mview: View){
        startActivity(Intent(this, SettingsActivity::class.java))
        if (Explorefragment.inExplorefragment == true) Explorefragment.nav!!.closeMenu()
        else if (favouriteFragment.infavouriteFragment == true) favouriteFragment.nav!!.closeMenu()
        else if (historyfragment.inhistoryFragment == true) historyfragment.nav!!.closeMenu()
    }
    fun gotohome(mview:View){
        if (Explorefragment.inExplorefragment == true) Explorefragment.nav!!.closeMenu()
        else if (favouriteFragment.infavouriteFragment == true) favouriteFragment.nav!!.closeMenu()
        else if (historyfragment.inhistoryFragment == true) historyfragment.nav!!.closeMenu()
    }
}
