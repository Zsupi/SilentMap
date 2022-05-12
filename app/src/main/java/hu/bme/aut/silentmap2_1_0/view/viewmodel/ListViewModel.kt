package hu.bme.aut.silentmap2_1_0.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.data.alarm.repository.AlarmRepository
import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact
import hu.bme.aut.silentmap2_1_0.data.contact.repository.ContactRepository
import hu.bme.aut.silentmap2_1_0.data.application.Application
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.bme.aut.silentmap2_1_0.data.territory.repository.TerritoryRepository
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val territoryRepository: TerritoryRepository
    val territories: LiveData<List<Territory>>

    private val contactRepository: ContactRepository
    val contacts: LiveData<List<Contact>>

    private val alarmRepository: AlarmRepository
    val alarms: LiveData<List<Alarm>>

    init {
        val territoryDao = Application.territoryDatabase.territoryDao()
        territoryRepository = TerritoryRepository(territoryDao)
        territories = territoryRepository.getAllTerritory()

        val contactData = Application.contactDatabase.contactDao()
        contactRepository = ContactRepository(contactData)
        contacts = contactRepository.getAllContact()

        val alarmDao = Application.alarmDatabase.alarmDao()
        alarmRepository = AlarmRepository(alarmDao)
        alarms = alarmRepository.getAllAlarm()
    }

    fun deleteTerritory(Territory: Territory) = viewModelScope.launch {
        territoryRepository.delete(Territory)
    }

    fun updateTerritory(Territory: Territory) = viewModelScope.launch {
        territoryRepository.update(Territory)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        contactRepository.delete(contact)
    }


}