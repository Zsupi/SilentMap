package hu.bme.aut.silentmap2_1_0.data.territory.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory

@Dao
interface TerritoryDao {

    @Insert
    fun insertTerritory(territory: Territory)

    @Query("SELECT * FROM territory")
    fun getAllTerritory(): LiveData<List<Territory>>

    @Query("SELECT * FROM territory")
    fun getAllTerritoryInList(): List<Territory>

    @Update
    fun updateTerritory(territory: Territory)

    @Delete
    fun deleteTerritory(territory: Territory)
}