package com.icebeal.gitxclone.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.icebeal.gitxclone.R
import com.icebeal.gitxclone.ui.activity.AboutActivity
import com.icebeal.gitxclone.ui.receiver.AlarmReceiver

class PreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER:String
    private lateinit var ABOUT:String
    private lateinit var THEME:String

    private lateinit var aboutPreference : Preference
    private lateinit var reminderPreference : SwitchPreferenceCompat
    private lateinit var themePreference: SwitchPreferenceCompat

    private lateinit var alarmReceiver : AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        addPreferencesFromResource(R.xml.preferences)

        alarmReceiver = AlarmReceiver()

        init()

        checkTheme()

    }

    private fun init(){

        REMINDER = getString(R.string.reminder_key)
        ABOUT = getString(R.string.about_key)
        THEME = getString(R.string.theme_key)

        reminderPreference = findPreference<SwitchPreferenceCompat>(REMINDER) as SwitchPreferenceCompat
        aboutPreference = findPreference<Preference>(ABOUT) as Preference
        themePreference = findPreference<SwitchPreferenceCompat>(THEME) as SwitchPreferenceCompat

        val sp = preferenceManager.sharedPreferences
        reminderPreference.isChecked = sp.getBoolean(REMINDER, false)
        themePreference.isChecked = sp.getBoolean(THEME, false)

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {

        when(preference){

            aboutPreference -> {

                val intent = Intent(requireContext(), AboutActivity::class.java)

                startActivity(intent)

            }

        }

        return super.onPreferenceTreeClick(preference)

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {

        when(key){

            REMINDER -> reminderPreference.isChecked = sharedPreferences.getBoolean(REMINDER, false)

            THEME -> themePreference.isChecked = sharedPreferences.getBoolean(THEME, false)

        }

        val state = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(REMINDER, false)
        alarmReceiver.set(requireContext(), state)

        checkTheme()

    }

    private fun checkTheme(){

        if(themePreference.isChecked){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        } else {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }

    }

}