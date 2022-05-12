package hu.bme.aut.silentmap2_1_0.logic.phonecall

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

import android.telephony.TelephonyManager
import android.telephony.PhoneStateListener







class SavedContactCallBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val prefs = context.getSharedPreferences("territoryPrefs", Context.MODE_PRIVATE)
        val reachedTerritories = prefs.getInt("reachedTerritories", 0)

        when (intent.action){
            "android.intent.action.PHONE_STATE" -> {
                val telephony =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                telephony.listen(object : PhoneStateListener() {
                    override fun onCallStateChanged(state: Int, incomingNumber: String) {
                        super.onCallStateChanged(state, incomingNumber)
                        println("incomingNumber : $incomingNumber")
                    }
                }, PhoneStateListener.LISTEN_CALL_STATE)
            }
        }
    }
}