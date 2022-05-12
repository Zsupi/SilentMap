package hu.bme.aut.silentmap2_1_0.data.alarm.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm

@Dao
interface AlarmDao {

    @Insert
    fun insertAlarm(alarm: Alarm)

    @Query("SELECT * FROM alarm")
    fun getAllAlarm(): LiveData<List<Alarm>>

    @Update
    fun updateAlarm(alarm: Alarm)

    @Delete
    fun deleteAlarm(alarm: Alarm)
}