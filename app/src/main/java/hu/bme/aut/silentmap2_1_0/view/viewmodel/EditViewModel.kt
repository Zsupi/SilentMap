package hu.nemeth.android.silentmap.view.viewmodel

import android.widget.RadioButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.nemeth.android.silentmap.R
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.bme.aut.silentmap2_1_0.data.territory.repository.TerritoryRepository
import kotlinx.coroutines.launch

class EditViewModel: ViewModel() {

    private val territoryRepository: TerritoryRepository

    init{
        val territoryDao = Application.territoryDatabase.territoryDao()
        territoryRepository = TerritoryRepository(territoryDao)
    }

    fun update(territory: Territory) = viewModelScope.launch {
        territoryRepository.update(territory)
    }

    fun convertRadioButton(territory: Territory): Int{
        return when(territory.color){
            Territory.TerrColor.RED -> R.id.rbRed
            Territory.TerrColor.BLUE -> R.id.rbBlue
            Territory.TerrColor.YELLOW -> R.id.rbOrange
            Territory.TerrColor.GREEN -> R.id.rbGreen
            Territory.TerrColor.WHITE -> R.id.rbWhite
        }
    }

    fun convertColor(selectedButton: RadioButton): Territory.TerrColor{
        return when(selectedButton.id){
            R.id.rbRed -> Territory.TerrColor.RED
            R.id.rbBlue -> Territory.TerrColor.BLUE
            R.id.rbOrange -> Territory.TerrColor.YELLOW
            R.id.rbGreen -> Territory.TerrColor.GREEN
            R.id.rbWhite -> Territory.TerrColor.WHITE
            else -> {
                Territory.TerrColor.WHITE}
        }
    }
}