package hu.bme.aut.silentmap2_1_0.logic.audio

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.widget.Toast


class AudioHelper (context: Context){


    private val audiomanager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val prefs: SharedPreferences = context.getSharedPreferences("audioPrefs", Context.MODE_PRIVATE)

    /**
     * @return true ha csak egy darab terulet van hatra vagy egy alarm maradt
     *         mert egyeb esewtben meg nemitott allapotban kell tartani
     */
    private fun canBeUnMuted(): Boolean{
        val reachedTerritories = prefs.getInt("reachedTerritories", 0)
        val nAlarmTimes= prefs.getInt("in_alarm_time", 0)
        return (nAlarmTimes <= 1 && reachedTerritories == 0) ||
                (nAlarmTimes == 0 && reachedTerritories <= 1)
    }

    fun mutePhone() {
        if (notificationManager.isNotificationPolicyAccessGranted){
            val previousRingMode = audiomanager.ringerMode
            prefs.edit().putInt("previous_ring_mode", previousRingMode).apply()
            audiomanager.ringerMode = AudioManager.RINGER_MODE_SILENT
        }
    }

    fun unMutePhone(): Boolean{
        if (notificationManager.isNotificationPolicyAccessGranted){
            if (canBeUnMuted()){
                val previousRingMode = prefs.getInt("previous_ring_mode", AudioManager.RINGER_MODE_NORMAL)
                audiomanager.ringerMode = previousRingMode
                return true
            }
        }
        return false
    }
}

//TODO