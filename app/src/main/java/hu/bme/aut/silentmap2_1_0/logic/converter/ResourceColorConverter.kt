package hu.nemeth.android.silentmap.logic.converter

import android.content.Context
import androidx.core.content.ContextCompat
import hu.nemeth.android.silentmap.R
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory

class ResourceColorConverter {

    companion object{
        fun convertStrokeColor(context: Context?, territory: Territory): Int{
            return when(territory.color){
                Territory.TerrColor.WHITE -> ContextCompat.getColor(context!!, R.color.white)
                Territory.TerrColor.BLUE -> ContextCompat.getColor(context!!, R.color.blue)
                Territory.TerrColor.RED -> ContextCompat.getColor(context!!, R.color.danger)
                Territory.TerrColor.YELLOW -> ContextCompat.getColor(context!!, R.color.warning)
                Territory.TerrColor.GREEN -> ContextCompat.getColor(context!!, R.color.accept)
            }
        }

        fun convertFillColor(context: Context?, territory: Territory): Int {
            return when(territory.color){
                Territory.TerrColor.WHITE -> ContextCompat.getColor(context!!, R.color.white_50a)
                Territory.TerrColor.BLUE -> ContextCompat.getColor(context!!, R.color.blue_50a)
                Territory.TerrColor.RED -> ContextCompat.getColor(context!!, R.color.danger_50a)
                Territory.TerrColor.YELLOW -> ContextCompat.getColor(context!!, R.color.warning_50a)
                Territory.TerrColor.GREEN -> ContextCompat.getColor(context!!, R.color.accept_50a)
            }
        }

        fun convertDrawable(territory: Territory): Int{
            return when(territory.color){
                Territory.TerrColor.WHITE -> R.drawable.color_corner_white
                Territory.TerrColor.BLUE -> R.drawable.color_corner_blue
                Territory.TerrColor.RED -> R.drawable.color_corner_red
                Territory.TerrColor.YELLOW -> R.drawable.color_corner_yellow
                Territory.TerrColor.GREEN -> R.drawable.color_corner_green
            }
        }
    }
}