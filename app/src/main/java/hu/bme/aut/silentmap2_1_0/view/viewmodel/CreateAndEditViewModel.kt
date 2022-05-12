package hu.bme.aut.silentmap2_1_0.view.viewmodel

import android.content.Context
import android.graphics.Color
import android.widget.RadioButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.*
import hu.nemeth.android.silentmap.R
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.bme.aut.silentmap2_1_0.data.territory.repository.TerritoryRepository
import hu.nemeth.android.silentmap.logic.converter.ResourceColorConverter.Companion.convertFillColor
import hu.nemeth.android.silentmap.logic.converter.ResourceColorConverter.Companion.convertStrokeColor
import kotlinx.coroutines.launch
import kotlin.random.Random

class CreateAndEditViewModel: ViewModel() {

    private val territoryRepository: TerritoryRepository
    private var newTerritory = Territory(Random.nextInt())
    private var oldTerritory = Territory(Random.nextInt())
    private var drawable = false
    var isEdit = false
        private set

    init {
        val territoryDao = Application.territoryDatabase.territoryDao()
        territoryRepository = TerritoryRepository(territoryDao)
    }

    fun setEditTerritory(territory: Territory){
        isEdit = true
        oldTerritory = territory.copy()
        newTerritory = territory
    }

    fun isDrawable(): Boolean {
        return drawable
    }

    fun updateRadius(radius: Double){
        newTerritory.radius = radius
    }

    fun updatePosition(latitude: Double, longitude: Double){
        newTerritory.latitude = latitude
        newTerritory.longitude = longitude
        drawable = true
    }

    fun updateColor(selectedButton: RadioButton){
        newTerritory.color = when(selectedButton.id){
            R.id.rbRed -> Territory.TerrColor.RED
            R.id.rbBlue -> Territory.TerrColor.BLUE
            R.id.rbOrange -> Territory.TerrColor.YELLOW
            R.id.rbGreen -> Territory.TerrColor.GREEN
            R.id.rbWhite -> Territory.TerrColor.WHITE
            else -> {
                Territory.TerrColor.WHITE}
        }
    }

    fun updateTitle(title: String){
        newTerritory.title = title
    }

    fun getOldCircle(): CircleOptions{
        return CircleOptions()
            .center(LatLng(oldTerritory.latitude, oldTerritory.longitude))
            .radius(oldTerritory.radius)
            .fillColor(Color.argb(50, 175, 175, 175))
            .strokeColor(Color.argb(100, 175, 175, 175))
    }

    fun getOldMarker(): MarkerOptions{
        return MarkerOptions()
            .position(LatLng(oldTerritory.latitude, oldTerritory.longitude))
            .title(oldTerritory.title)
    }

    fun getCircle(context: Context?): CircleOptions {
        return CircleOptions()
            .center(LatLng(newTerritory.latitude, newTerritory.longitude))
            .radius(newTerritory.radius)
            .fillColor(convertFillColor(context, newTerritory))
            .strokeColor(convertStrokeColor(context, newTerritory))
    }
    
    fun getMarker(): MarkerOptions{
        return MarkerOptions()
            .position(LatLng(newTerritory.latitude, newTerritory.longitude))
            .title(newTerritory.title)
    }

    fun insertOrUpdate() = viewModelScope.launch {
        if (isEdit){
            territoryRepository.update(newTerritory)
        }
        else{
            territoryRepository.insert(newTerritory)
        }
    }






}