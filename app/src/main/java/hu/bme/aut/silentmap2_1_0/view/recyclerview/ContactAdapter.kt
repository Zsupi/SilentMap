package hu.bme.aut.silentmap2_1_0.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact
import hu.bme.aut.silentmap2_1_0.view.callback.UpdateContactCallback
import hu.nemeth.android.silentmap.databinding.CardContactLayoutBinding

class ContactAdapter(updateCallback: UpdateContactCallback) : ListAdapter<Contact, ContactAdapter.ViewHolder>(itemCallback) {
    companion object {
        object itemCallback : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var binding: CardContactLayoutBinding
    private val callback = updateCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        binding = CardContactLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        val contact = this.getItem(position)
        holder.contact = contact
        holder.tvName.text = contact.name
        holder.tvNumber.text = contact.number
        holder.itemView.setOnClickListener {
            callback.itemClicked(contact)
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvName = binding.tvName
        val tvNumber = binding.tvNumber
        var contact: Contact? = null
    }


}