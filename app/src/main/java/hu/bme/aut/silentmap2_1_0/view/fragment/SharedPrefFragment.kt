package hu.nemeth.android.silentmap.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import hu.nemeth.android.silentmap.R

class SharedPrefFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    companion object{
        const val KEY_INTERVAL_NUMBER = "interval_number"
        const val KEY_BACKGROUND = "run_background"

        var STATE_BACKGROUND = false
        var STATE_INTERVAL_NUMBER = 30_000L

        fun settingsChanged(sharedPreferences: SharedPreferences){
            STATE_INTERVAL_NUMBER = when (sharedPreferences.getString(KEY_INTERVAL_NUMBER, "")){
                "15 sec" -> 15_000L
                "60 sec" -> 60_000L
                else -> 30_000L
            }

            STATE_BACKGROUND = sharedPreferences.getBoolean(KEY_BACKGROUND, false)
            Log.d("BACKGROUND", STATE_BACKGROUND.toString())
        }

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        when(key){
            KEY_INTERVAL_NUMBER, KEY_BACKGROUND ->{
                settingsChanged(sharedPreferences)
            }
        }
    }
}