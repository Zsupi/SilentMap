package hu.bme.aut.silentmap2_1_0.view.viewmodel

import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact
import hu.bme.aut.silentmap2_1_0.data.contact.repository.ContactRepository
import kotlinx.coroutines.launch

class AddContactViewModel: ViewModel() {


    private val contactRepository: ContactRepository
    val savedContacts: LiveData<List<Contact>>

    var phoneContacts = mutableListOf<Contact>()
        private set

    private val mProjection: Array<String> = arrayOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    )

    init {
        val contactData = Application.contactDatabase.contactDao()
        contactRepository = ContactRepository(contactData)
        savedContacts = contactRepository.getAllContact()
    }

    fun loadPhoneContacts(contentResolver: ContentResolver){
        val contacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, mProjection, null, null, null)

        while (contacts?.moveToNext() == true){
            val name = contacts.getString(contacts.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = contacts.getString(contacts.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val id = contacts.getString(contacts.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone._ID))
            phoneContacts.add(Contact(id.toInt(), name, number))
        }
        contacts?.close()

        phoneContacts.sortBy {
            it.name
        }
        phoneContacts.distinctBy {
            it.number
        }
    }

    fun removeSaved(){
        phoneContacts.removeAll {
            savedContacts.value?.contains(it) == true
        }
    }

    fun insert(contact: Contact) = viewModelScope.launch{
        contactRepository.insert(contact)
    }

}