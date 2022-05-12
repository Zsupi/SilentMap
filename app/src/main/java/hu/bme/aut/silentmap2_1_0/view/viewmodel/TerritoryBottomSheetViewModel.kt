package hu.nemeth.android.silentmap.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.bme.aut.silentmap2_1_0.data.territory.repository.TerritoryRepository
import kotlinx.coroutines.launch

class TerritoryBottomSheetViewModel() : ViewModel() {

    private val territoryRepository: TerritoryRepository
    private val territories: LiveData<List<Territory>>

    init {
        val territoryDao = Application.territoryDatabase.territoryDao()
        territoryRepository = TerritoryRepository(territoryDao)
        territories = territoryRepository.getAllTerritory()
    }

    fun delete(Territory: Territory) = viewModelScope.launch {
        territoryRepository.delete(Territory)
    }

    fun update(Territory: Territory) = viewModelScope.launch {
        territoryRepository.update(Territory)
    }

}