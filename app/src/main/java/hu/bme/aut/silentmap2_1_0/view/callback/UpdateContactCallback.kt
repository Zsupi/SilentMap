package hu.bme.aut.silentmap2_1_0.view.callback

import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact

interface UpdateContactCallback {
    fun itemClicked(contact: Contact)
}