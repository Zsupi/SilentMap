package hu.bme.aut.silentmap2_1_0.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.nemeth.android.silentmap.MainActivity
import hu.nemeth.android.silentmap.R
import hu.nemeth.android.silentmap.databinding.FragmentSettingsBinding
import kotlinx.coroutines.withContext
import java.util.*

class SettingsFragment: Fragment(){
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var pref: SharedPreferences
    private lateinit var language: String
    private val items = listOf("En", "Hu")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        language = pref.getString("Language", "En").toString()

        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, R.id.tvItem, items)

        binding.swDarkMode.isChecked = pref.getBoolean("NightMode", false)

        binding.languageMenu.adapter = adapter

        binding.swDarkMode.setOnClickListener{
            if (binding.swDarkMode.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                pref.edit().putBoolean("NightMode", true).apply()
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                pref.edit().putBoolean("NightMode", false).apply()
            }

        }

        binding.languageMenu.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                changeLanguage(items[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nothing
            }
        }

        binding.swAlarm.isChecked = pref.getBoolean("AccurateAlarm", false)
        binding.swAlarm.setOnClickListener{
            if (binding.swAlarm.isChecked){
                pref.edit().putBoolean("AccurateAlarm", true).apply()
            }else{
                pref.edit().putBoolean("AccurateAlarm", true).apply()
            }
        }

        binding.swNotification.isChecked = pref.getBoolean("Notification", false)
        binding.swNotification.setOnClickListener {
            if (binding.swAlarm.isChecked){
                pref.edit().putBoolean("Notification", true).apply()
            }else{
                pref.edit().putBoolean("Notification", true).apply()
            }
        }


    }


    private fun changeLanguage(language: String){
        if (!this.language.equals(language)){
            activity.let {
                val mainActivity = activity as MainActivity
                mainActivity.updateLocale(Locale(language))
                pref.edit().putString("Language", language).apply()
            }
        }
    }
}