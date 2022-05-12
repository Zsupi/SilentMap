package hu.bme.aut.silentmap2_1_0.data.contact.repository

import androidx.lifecycle.LiveData
import hu.bme.aut.silentmap2_1_0.data.contact.dao.ContactDao
import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepository(private val contactDao: ContactDao) {

    fun getAllContact(): LiveData<List<Contact>>{
        return contactDao.getAllContact()
    }

    suspend fun insert(contact: Contact) = withContext(Dispatchers.IO){
        contactDao.insertContact(contact)
    }

    suspend fun delete(contact: Contact) = withContext(Dispatchers.IO){
        contactDao.deleteContact(contact)
    }
}
