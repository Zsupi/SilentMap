package hu.bme.aut.silentmap2_1_0.data.territory.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "territory")
@Parcelize
data class Territory(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var radius: Double = 1.0,
    var color: TerrColor = TerrColor.WHITE,
    var title: String = "Default",
    var longitude: Double = 19.040236,
    var latitude: Double = 47.497913
):Parcelable{
    enum class TerrColor{
        RED, GREEN, YELLOW, BLUE, WHITE
    }
}

class ColorConverter{
    companion object{
        const val RED = "RED"
        const val GREEN = "GREEN"
        const val YELLOW = "YELLOW"
        const val BLUE = "BLUE"
        const val WHITE = "WHITE"
    }

    @TypeConverter
    fun toColor(value: String?): Territory.TerrColor {
        return when(value){
            RED -> Territory.TerrColor.RED
            GREEN -> Territory.TerrColor.GREEN
            YELLOW -> Territory.TerrColor.YELLOW
            BLUE -> Territory.TerrColor.BLUE
            WHITE -> Territory.TerrColor.WHITE
            else -> Territory.TerrColor.WHITE
        }
    }

    @TypeConverter
    fun toString(value: Territory.TerrColor): String?{
        return value.name;
    }
}