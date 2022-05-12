package hu.bme.aut.silentmap2_1_0.view.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.data.alarm.repository.AlarmRepository
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.bme.aut.silentmap2_1_0.logic.alarm.AlarmHelper
import kotlinx.coroutines.launch

class AlarmBottomSheetViewModel: ViewModel() {

    private val alarmRepository: AlarmRepository

    init {
        val alarmDao = Application.alarmDatabase.alarmDao()
        alarmRepository = AlarmRepository(alarmDao)
    }

    fun delete(alarm: Alarm, context: Context) = viewModelScope.launch{
        AlarmHelper.cancelAlarm(alarm, context)
        alarmRepository.delete(alarm)
    }

    fun update(alarm: Alarm) = viewModelScope.launch {
        alarmRepository.update(alarm)
    }
}