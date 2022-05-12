package hu.nemeth.android.silentmap.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.nemeth.android.silentmap.databinding.CardLayoutBinding
import hu.nemeth.android.silentmap.view.callback.UpdateTerritoryCallback

class TerritoryAdapter(
    updateCallback: UpdateTerritoryCallback,
    getDrawable: (input: Territory) -> Int
    ) : ListAdapter<Territory, TerritoryAdapter.ViewHolder>(itemCallback) {

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Territory>() {
            override fun areItemsTheSame(oldItem: Territory, newItem: Territory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Territory, newItem: Territory): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var binding: CardLayoutBinding
    private val callback = updateCallback
    private val converter = getDrawable

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val territory = this.getItem(position)

        holder.Territory = territory
        holder.tvTitle.text = territory.title
        holder.itemView.setOnClickListener{
            callback.itemClicked(territory)
        }
        val colorPicture = converter(territory)

        holder.ivColor.setImageResource(colorPicture)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTitle = binding.tvTitle
        val ivColor = binding.ivColor

        var Territory: Territory? = null
    }
}