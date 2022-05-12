package hu.bme.aut.silentmap2_1_0.logic.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.logic.converter.TimeConverter
import java.util.*

class AlarmHelper {

    companion object{
        fun createAlarm(alarm: Alarm, context: Context){
            val pref = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
            val accurateAlarm = pref.getBoolean("AccurateAlarm", false)

            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = TimeConverter.hour(alarm.start)
            calendar[Calendar.MINUTE] = TimeConverter.minute(alarm.start)
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            var intent = Intent(context, AlarmBroadcastReceiver::class.java)
            intent.putExtra("is_start_time", true)
            intent.putExtra("triggered_alarm", alarm)

            var pendingIntent = PendingIntent.getBroadcast(context, alarm.id, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            if (!accurateAlarm){
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent
                )
            }else{
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent
                )
            }


            calendar[Calendar.HOUR_OF_DAY] = TimeConverter.hour(alarm.end)
            calendar[Calendar.MINUTE] = TimeConverter.minute(alarm.end)

            intent = Intent(context, AlarmBroadcastReceiver::class.java)
            intent.putExtra("is_start_time", false)
            intent.putExtra("triggered_alarm", alarm)

            pendingIntent = PendingIntent.getBroadcast(context, -1 * (alarm.id), intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            if (!accurateAlarm){
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent
                )
            }else{
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent
                )
            }

        }

        fun cancelAlarm(alarm: Alarm, context: Context){
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            var intent = Intent(context, AlarmBroadcastReceiver::class.java)
            intent.putExtra("is_start_time", true)
            intent.putExtra("triggered_alarm", alarm)

            var pendingIntent = PendingIntent.getBroadcast(context, alarm.id, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.cancel(pendingIntent)

            intent = Intent(context, AlarmBroadcastReceiver::class.java)
            intent.putExtra("is_start_time", false)
            intent.putExtra("triggered_alarm", alarm)

            pendingIntent = PendingIntent.getBroadcast(context, -1 * (alarm.id), intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.cancel(pendingIntent)

        }

        fun updateAlarm(alarm: Alarm, context: Context){
            cancelAlarm(alarm, context)
            createAlarm(alarm, context)
        }
    }

}