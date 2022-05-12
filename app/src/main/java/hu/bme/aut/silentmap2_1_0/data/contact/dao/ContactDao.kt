package hu.bme.aut.silentmap2_1_0.data.contact.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact

@Dao
interface ContactDao {
    @Insert
    fun insertContact(contact: Contact)

    @Query("SELECT * FROM contact")
    fun getAllContact(): LiveData<List<Contact>>

    @Delete
    fun deleteContact(contact: Contact)
}