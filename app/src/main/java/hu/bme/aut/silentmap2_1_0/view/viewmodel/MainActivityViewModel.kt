package hu.bme.aut.silentmap2_1_0.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.data.alarm.repository.AlarmRepository
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.bme.aut.silentmap2_1_0.data.territory.repository.TerritoryRepository

class MainActivityViewModel(): ViewModel() {

    private val territoryRepository: TerritoryRepository
    val territories: LiveData<List<Territory>>

    init {
        val territoryDao = Application.territoryDatabase.territoryDao()
        territoryRepository = TerritoryRepository(territoryDao)
        territories = territoryRepository.getAllTerritory()
    }
}