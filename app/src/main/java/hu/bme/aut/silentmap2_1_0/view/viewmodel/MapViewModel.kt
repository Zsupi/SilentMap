package hu.nemeth.android.silentmap.view.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.*
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.bme.aut.silentmap2_1_0.data.territory.repository.TerritoryRepository
import hu.nemeth.android.silentmap.logic.converter.ResourceColorConverter.Companion.convertFillColor
import hu.nemeth.android.silentmap.logic.converter.ResourceColorConverter.Companion.convertStrokeColor

class MapViewModel : ViewModel(){

    private val territoryRepository: TerritoryRepository
    private val territories: LiveData<List<Territory>>

    init{
        val territoryDao = Application.territoryDatabase.territoryDao()
        territoryRepository = TerritoryRepository(territoryDao)
        territories = territoryRepository.getAllTerritory()
    }

    fun getTerritories(): LiveData<List<Territory>> {
        return territories
    }

    fun getCircleOptions(context: Context?): List<CircleOptions> {
        val circleOptions = mutableListOf<CircleOptions>()
        for (terr in territories.value!!){
            val circleOption = CircleOptions()
                .center(LatLng(terr.latitude, terr.longitude))
                .radius(terr.radius)
                .fillColor(convertFillColor(context, terr))
                .strokeColor(convertStrokeColor(context, terr))
            circleOptions.add(circleOption)
        }
        return circleOptions.toList()
    }

    fun getMarkerOptions(): List<MarkerOptions> {
        val markerOptions = mutableListOf<MarkerOptions>()
        for (terr in territories.value!!){
            val markerOption = MarkerOptions()
                .position(LatLng(terr.latitude, terr.longitude))
                .title(terr.title)
            markerOptions.add(markerOption)
        }

        return markerOptions.toList()
    }
}