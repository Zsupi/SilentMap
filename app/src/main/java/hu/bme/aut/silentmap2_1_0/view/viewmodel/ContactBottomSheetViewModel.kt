package hu.bme.aut.silentmap2_1_0.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact
import hu.bme.aut.silentmap2_1_0.data.contact.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactBottomSheetViewModel: ViewModel() {

    private val contactRepository: ContactRepository

    init{
        val contactDao = Application.contactDatabase.contactDao()
        contactRepository = ContactRepository(contactDao)
    }

    fun delete(contact: Contact) = viewModelScope.launch {
        contactRepository.delete(contact)
    }
}