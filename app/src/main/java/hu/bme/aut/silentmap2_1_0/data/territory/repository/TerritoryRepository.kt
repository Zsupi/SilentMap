package hu.bme.aut.silentmap2_1_0.data.territory.repository

import androidx.lifecycle.LiveData
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.bme.aut.silentmap2_1_0.data.territory.dao.TerritoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TerritoryRepository(private val territoryDao: TerritoryDao) {

    fun getAllTerritory(): LiveData<List<Territory>>{
        return territoryDao.getAllTerritory()
    }

    suspend fun getAllTerritoryInList(): List<Territory>{
        return withContext(Dispatchers.IO) {
            territoryDao.getAllTerritoryInList()
        }
    }

    suspend fun insert(Territory: Territory) = withContext(Dispatchers.IO){
        territoryDao.insertTerritory(Territory)
    }

    suspend fun delete(Territory: Territory) = withContext(Dispatchers.IO){
        territoryDao.deleteTerritory(Territory)
    }

    suspend fun update(Territory: Territory) = withContext(Dispatchers.IO){
        territoryDao.updateTerritory(Territory)
    }


}