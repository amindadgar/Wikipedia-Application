package com.amindadgar.intowikipedia.Activities


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.preference.*
import android.text.TextUtils
import android.view.MenuItem
import com.amindadgar.intowikipedia.R

class SettingsActivity : PreferenceActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().add(android.R.id.content,generalSetting()).commit()


    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    class generalSetting:PreferenceFragment(){

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_general)

            val sharedPreferences = activity.getSharedPreferences("LayoutMode", Context.MODE_PRIVATE)
            val back = findPreference("Goback")
            back.setOnPreferenceClickListener {
                activity.finish()
                true
            }

            val faq = findPreference("FAQ")
            faq.setOnPreferenceClickListener {
                val openurl = Intent(android.content.Intent.ACTION_VIEW)

                openurl.data = Uri.parse("http://t.me/mramin55")
                startActivity(openurl)
                true
            }
            val Layoutmanager = findPreference("layoutmanager")
            Layoutmanager.setOnPreferenceChangeListener { _, newValue ->
                val sharedPrefencesEditor = sharedPreferences.edit()
                sharedPrefencesEditor.putString("LayoutManager",newValue.toString())
                sharedPrefencesEditor.apply()
                sharedPrefencesEditor.commit()
                true
            }

            val notificationList = findPreference("notificationList")
            notificationList.setOnPreferenceChangeListener { _, newValue ->
                val sharedPrefencesEditor = sharedPreferences.edit()
                sharedPrefencesEditor.putString("notificationTime",newValue.toString())
                sharedPrefencesEditor.apply()
                sharedPrefencesEditor.commit()
                true
            }
            val About = findPreference("About")
            About.setOnPreferenceClickListener {
                startActivity(Intent(activity,AboutActivity::class.java))
                true
            }
        }
    }

    override fun onIsMultiPane(): Boolean {
        return isXLargeTablet(this)
    }

    companion object {

        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
            val stringValue = value.toString()

            if (preference is ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                val listPreference = preference
                val index = listPreference.findIndexOfValue(stringValue)

                // Set the summary to reflect the new value.
                preference.setSummary(
                    if (index >= 0)
                        listPreference.entries[index]
                    else
                        null
                )

            } else if (preference is RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary("Silent")

                } else {
                    val ringtone = RingtoneManager.getRingtone(
                        preference.getContext(), Uri.parse(stringValue)
                    )

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null)
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        val name = ringtone.getTitle(preference.getContext())
                        preference.setSummary(name)
                    }
                }

            }
            else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.summary = stringValue
            }
            true
        }

        private fun isXLargeTablet(context: Context): Boolean {
            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE
        }
        private fun bindPreferenceSummaryToValue(preference: Preference) {
            // Set the listener to watch for value changes.
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            // Trigger the listener immediately with the preference's
            // current value.
            sBindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager
                    .getDefaultSharedPreferences(preference.context)
                    .getString(preference.key, "")
            )
        }
    }
}
