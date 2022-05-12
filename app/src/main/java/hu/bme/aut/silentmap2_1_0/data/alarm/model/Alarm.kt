package hu.bme.aut.silentmap2_1_0.data.alarm.model

import android.os.Parcelable
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Entity(tableName = "alarm")
@Parcelize
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var start: Int = 0,
    var end: Int = 0,
    var repeat: ArrayList<Days> = arrayListOf()
): Parcelable{
    enum class Days{
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    companion object{
        const val TIME_FORMAT = "HH:mm"
    }

}

class RepeatConverter{
    companion object{
        const val MONDAY = "MONDAY"
        const val TUESDAY = "TUESDAY"
        const val WEDNESDAY = "WEDNESDAY"
        const val THURSDAY = "THURSDAY"
        const val FRIDAY = "FRIDAY"
        const val SATURDAY = "SATURDAY"
        const val SUNDAY = "SUNDAY"
    }

    @TypeConverter
    fun fromArrayList(repeat: ArrayList<Alarm.Days>):String{
        val stringRepeat = arrayListOf<String>()
        for (day in repeat){
            when(day){
                Alarm.Days.MONDAY -> stringRepeat.add(MONDAY)
                Alarm.Days.TUESDAY -> stringRepeat.add(TUESDAY)
                Alarm.Days.WEDNESDAY -> stringRepeat.add(WEDNESDAY)
                Alarm.Days.THURSDAY -> stringRepeat.add(THURSDAY)
                Alarm.Days.FRIDAY -> stringRepeat.add(FRIDAY)
                Alarm.Days.SATURDAY -> stringRepeat.add(SATURDAY)
                Alarm.Days.SUNDAY -> stringRepeat.add(SUNDAY)
            }
        }

        val json = Gson().toJson(stringRepeat)
        return json
    }

    @TypeConverter
    fun fromString(repeat: String):ArrayList<Alarm.Days>{
        val listType: Type = object : TypeToken<ArrayList<String>>() {}.type
        val stringRepeat: ArrayList<String> = Gson().fromJson(repeat, listType)
        val dayRepeat = arrayListOf<Alarm.Days>()
        for (day in stringRepeat){
            when(day){
                MONDAY -> dayRepeat.add(Alarm.Days.MONDAY)
                TUESDAY -> dayRepeat.add(Alarm.Days.TUESDAY)
                WEDNESDAY -> dayRepeat.add(Alarm.Days.WEDNESDAY)
                THURSDAY -> dayRepeat.add(Alarm.Days.THURSDAY)
                FRIDAY -> dayRepeat.add(Alarm.Days.FRIDAY)
                SATURDAY -> dayRepeat.add(Alarm.Days.SATURDAY )
                SUNDAY -> dayRepeat.add(Alarm.Days.SUNDAY)
            }
        }

        return dayRepeat
    }
}