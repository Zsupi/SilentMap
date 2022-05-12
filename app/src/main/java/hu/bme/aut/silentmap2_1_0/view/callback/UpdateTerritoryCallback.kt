package hu.nemeth.android.silentmap.view.callback

import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory

interface UpdateTerritoryCallback{
    fun itemClicked(territory: Territory)
}