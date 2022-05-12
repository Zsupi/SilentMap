package hu.nemeth.android.silentmap.logic.geofence

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory

class GeofenceHelper(base: Context) : ContextWrapper(base) {


    private val TAG = "GeofenceHelper"

    private var pendingIntent: PendingIntent? = null
    private var geofences = mutableListOf<Geofence>()
    fun getGeofencingRequest(): GeofencingRequest{
        return GeofencingRequest.Builder()
            .addGeofences(geofences)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()
    }

    fun generateGeofences(territories: List<Territory>?){
        if (territories != null) {
            for (terr in territories){
                createGeofence(terr)
            }
        }
    }

    fun getPendingIntent(): PendingIntent{
        if (pendingIntent != null){
            return pendingIntent as PendingIntent
        }
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 2607, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return pendingIntent as PendingIntent
    }

    private fun createGeofence(territory: Territory){
        val geofence = Geofence.Builder()
            .setCircularRegion(territory.latitude, territory.longitude, territory.radius.toFloat())
            .setRequestId(territory.id.toString())
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .setLoiteringDelay(5000)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()

        geofences.add(geofence)
    }

}