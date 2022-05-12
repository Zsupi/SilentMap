package hu.bme.aut.silentmap2_1_0.logic.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.media.AudioManager
import android.widget.Toast
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.logic.audio.AudioHelper

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val isStartTime = intent.getBooleanExtra("is_start_time", true)
        val triggeredAlarm = intent.getParcelableExtra<Alarm>("triggered_alarm")
        val audioHelper = AudioHelper(context)

        val calendar: Calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)
        var isToday = false

        when (day) {
            Calendar.MONDAY -> {
                isToday = triggeredAlarm?.repeat?.contains(Alarm.Days.MONDAY) == true
            }
            Calendar.TUESDAY -> {
                isToday = triggeredAlarm?.repeat?.contains(Alarm.Days.TUESDAY) == true
            }
            Calendar.WEDNESDAY -> {
                isToday = triggeredAlarm?.repeat?.contains(Alarm.Days.WEDNESDAY) == true
            }
            Calendar.THURSDAY -> {
                isToday = triggeredAlarm?.repeat?.contains(Alarm.Days.THURSDAY) == true
            }
            Calendar.FRIDAY -> {
                isToday = triggeredAlarm?.repeat?.contains(Alarm.Days.FRIDAY) == true
            }
            Calendar.SATURDAY -> {
                isToday = triggeredAlarm?.repeat?.contains(Alarm.Days.SATURDAY) == true
            }
            Calendar.SUNDAY -> {
                isToday = triggeredAlarm?.repeat?.contains(Alarm.Days.SUNDAY) == true
            }
        }

        val prefs = context.getSharedPreferences("audioPrefs", Context.MODE_PRIVATE)
        val nAlarmTimes= prefs.getInt("in_alarm_time", 0)

        if (isToday){
            if (isStartTime){
                audioHelper.mutePhone()
                prefs.edit().putInt("in_alarm_time", nAlarmTimes+1).apply()
            }
            else{
                if (!audioHelper.unMutePhone())
                    Toast.makeText(context,"The phone cannot be set to ring mode", Toast.LENGTH_LONG).show()
                prefs.edit().putInt("in_alarm_time", decreasePref(nAlarmTimes)).apply()
            }
        }

    }

    private fun decreasePref(pref: Int): Int{
        return if (pref < 0)
            0
        else
            pref-1
    }
}