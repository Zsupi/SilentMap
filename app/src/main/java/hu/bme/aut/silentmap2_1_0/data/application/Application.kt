package hu.bme.aut.silentmap2_1_0.data.application

import android.app.Application
import androidx.room.Room
import com.zeugmasolutions.localehelper.LocaleAwareApplication
import hu.bme.aut.silentmap2_1_0.data.alarm.database.AlarmDatabase
import hu.bme.aut.silentmap2_1_0.data.contact.database.ContactDatabase
import hu.bme.aut.silentmap2_1_0.data.territory.database.TerritoryDatabase

class Application : LocaleAwareApplication(){

    companion object {
        lateinit var territoryDatabase: TerritoryDatabase
            private set
        lateinit var contactDatabase: ContactDatabase
            private set
        lateinit var alarmDatabase: AlarmDatabase
    }

    override fun onCreate() {
        super.onCreate()

        territoryDatabase = Room.databaseBuilder(
            applicationContext,
            TerritoryDatabase::class.java,
            "territtory_database"
        ).fallbackToDestructiveMigration().build()

        contactDatabase = Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contact_database"
        ).fallbackToDestructiveMigration().build()

        alarmDatabase = Room.databaseBuilder(
            applicationContext,
            AlarmDatabase::class.java,
            "alarm_database"
        ).fallbackToDestructiveMigration().build()
    }
}