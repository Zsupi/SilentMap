package hu.bme.aut.silentmap2_1_0.view.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.data.alarm.repository.AlarmRepository
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.bme.aut.silentmap2_1_0.logic.alarm.AlarmHelper
import kotlinx.coroutines.launch
import kotlin.random.Random

class CreateAndEditAlarmViewModel: ViewModel() {
    private val alarmRepository: AlarmRepository
    private var alarms: LiveData<List<Alarm>>

    private var newAlarm = Alarm(Random.nextInt())

    var isEdit = false
        private set

    init{
        val alarmDao = Application.alarmDatabase.alarmDao()
        alarmRepository = AlarmRepository(alarmDao)
        alarms = alarmRepository.getAllAlarm()
    }

    fun insert(context: Context) = viewModelScope.launch{
        newAlarm.let {
            if (isEdit){
                alarmRepository.update(it)
                AlarmHelper.createAlarm(it, context)
            }
            else{
                alarmRepository.insert(it)
                AlarmHelper.updateAlarm(it, context)
            }
        }
    }

    fun setEdit(alarm: Alarm){
        newAlarm = alarm
        isEdit = true
    }

    fun addDay(day: Alarm.Days){
        newAlarm.repeat.add(day)
    }

    fun removeDay(day: Alarm.Days){
        newAlarm.repeat.remove(day)
    }

    fun addStartTime(hour: Int, minute: Int){
        newAlarm.start = hour * 60 + minute
    }

    fun addEndTime(hour: Int, minute: Int){
        newAlarm.end = hour * 60 + minute
    }


}