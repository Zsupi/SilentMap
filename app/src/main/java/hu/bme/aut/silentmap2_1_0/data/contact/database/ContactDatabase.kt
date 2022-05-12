package hu.bme.aut.silentmap2_1_0.data.contact.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.silentmap2_1_0.data.contact.dao.ContactDao
import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact

@Database(
    version = 1,
    exportSchema = false,
    entities = [Contact::class]
)
abstract class ContactDatabase:RoomDatabase() {
    abstract fun contactDao(): ContactDao
}