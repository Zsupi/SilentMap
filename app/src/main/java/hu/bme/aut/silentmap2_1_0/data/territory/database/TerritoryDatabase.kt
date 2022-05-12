package hu.bme.aut.silentmap2_1_0.data.territory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.silentmap2_1_0.data.territory.model.ColorConverter
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.bme.aut.silentmap2_1_0.data.territory.dao.TerritoryDao

@Database(
    version = 2,
    exportSchema = false,
    entities = [Territory::class]
)
@TypeConverters(
    ColorConverter::class
)
abstract class TerritoryDatabase :RoomDatabase(){

    abstract fun territoryDao(): TerritoryDao
}