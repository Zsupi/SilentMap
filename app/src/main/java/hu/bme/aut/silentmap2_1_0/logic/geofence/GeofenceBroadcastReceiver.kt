package hu.nemeth.android.silentmap.logic.geofence

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import hu.bme.aut.silentmap2_1_0.logic.audio.AudioHelper
import hu.bme.aut.silentmap2_1_0.logic.notification.NotificationCreator
import hu.nemeth.android.silentmap.R

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "GeofenceBroadcastReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        val event = GeofencingEvent.fromIntent(intent)
        val audioHelper = AudioHelper(context)

        if (event.hasError()){
            Log.e(TAG, "Error receiving geofence")
            return
        }

        val notificationCreator = NotificationCreator("GeofenceChannel", 1, context)
        notificationCreator.create("Geofence Notification", "Notification channel for geofencing", "Load location")
        val prefs = context.getSharedPreferences("audioPrefs", Context.MODE_PRIVATE)
        val reachedTerritories = prefs.getInt("reachedTerritories", 0)

        when(event.geofenceTransition){
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                audioHelper.mutePhone()
                notificationCreator.updateNotification("A territory reached")
                prefs.edit().putInt("reachedTerritories", reachedTerritories+1).apply()
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                if (audioHelper.unMutePhone())
                    notificationCreator.updateNotification("You have not reached a saved area")
                else
                    Toast.makeText(context,"The phone cannot be set to ring mode", Toast.LENGTH_LONG).show()
                prefs.edit().putInt("reachedTerritories", decreasePref(reachedTerritories)).apply()
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