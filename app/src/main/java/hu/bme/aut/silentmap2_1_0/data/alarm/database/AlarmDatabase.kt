package hu.bme.aut.silentmap2_1_0.data.alarm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import hu.bme.aut.silentmap2_1_0.data.alarm.dao.AlarmDao
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.data.alarm.model.RepeatConverter
import hu.bme.aut.silentmap2_1_0.data.territory.model.ColorConverter

@Database(
    version = 1,
    exportSchema = false,
    entities = [Alarm::class]
)
@TypeConverters(
    RepeatConverter::class
)
abstract class AlarmDatabase :RoomDatabase(){
    abstract fun alarmDao(): AlarmDao
}