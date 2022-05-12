package hu.bme.aut.silentmap2_1_0.logic.converter

import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm

abstract class TimeConverter {

    companion object{
        fun hour(time: Int): Int{
            return time / 60
        }
        fun minute(time: Int): Int{
            val hour = time / 60
            return time - hour * 60
        }

    }
}