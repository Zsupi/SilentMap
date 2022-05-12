package hu.bme.aut.silentmap2_1_0.data.alarm.repository

import androidx.lifecycle.LiveData
import hu.bme.aut.silentmap2_1_0.data.alarm.dao.AlarmDao
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.withContext

class AlarmRepository(private val alarmDao: AlarmDao) {

    fun getAllAlarm(): LiveData<List<Alarm>>{
        return alarmDao.getAllAlarm()
    }

    suspend fun insert(alarm: Alarm) = withContext(Dispatchers.IO){
        alarmDao.insertAlarm(alarm)
    }

    suspend fun delete(alarm: Alarm) = withContext(Dispatchers.IO){
        alarmDao.deleteAlarm(alarm)
    }

    suspend fun update(alarm: Alarm) = withContext(Dispatchers.IO){
        alarmDao.updateAlarm(alarm)
    }

}