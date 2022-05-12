package hu.bme.aut.silentmap2_1_0.view.callback

import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact

interface UpdateAlarmCallback {
    fun itemClicked(alarm: Alarm)
}