package hu.bme.aut.silentmap2_1_0.view.recyclerview

import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.logic.converter.TimeConverter
import hu.bme.aut.silentmap2_1_0.view.callback.UpdateAlarmCallback
import hu.nemeth.android.silentmap.R
import hu.nemeth.android.silentmap.databinding.CardAlarmLayoutBinding
import java.sql.Time
import java.util.*
import kotlin.math.floor

class AlarmAdapter(
    updateCallback: UpdateAlarmCallback
) : ListAdapter<Alarm, AlarmAdapter.ViewHolder>(itemCallback) {

    companion object{
       object itemCallback: DiffUtil.ItemCallback<Alarm>(){
           override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
               return oldItem.id == newItem.id
           }

           override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
               return oldItem == newItem
           }
       }
    }

    private lateinit var binding: CardAlarmLayoutBinding
    private val callback = updateCallback
    private lateinit var res: Resources

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CardAlarmLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        res = parent.resources
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarm = this.getItem(position)
        holder.alarm = alarm

        holder.tvStartTime.text = res.getString(R.string.card_alarm_time, TimeConverter.hour(alarm.start), TimeConverter.minute(alarm.start))
        holder.tvEndTime.text = res.getString(R.string.card_alarm_time, TimeConverter.hour(alarm.end), TimeConverter.minute(alarm.end))

        holder.itemView.setOnClickListener {
            callback.itemClicked(alarm)
        }
    }

   inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       val tvStartTime = binding.tvStartTime
       val tvEndTime = binding.tvEndTime
       var alarm: Alarm? = null
   }
}