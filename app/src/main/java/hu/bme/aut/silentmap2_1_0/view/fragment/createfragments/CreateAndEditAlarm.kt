package hu.bme.aut.silentmap2_1_0.view.fragment.createfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.logic.converter.TimeConverter
import hu.bme.aut.silentmap2_1_0.view.viewmodel.CreateAndEditAlarmViewModel
import hu.nemeth.android.silentmap.R
import hu.nemeth.android.silentmap.databinding.FragmentCreateAndEditAlarmBinding

class CreateAndEditAlarm : Fragment() {
    private lateinit var binding: FragmentCreateAndEditAlarmBinding
    private lateinit var viewModel: CreateAndEditAlarmViewModel

    private val args by navArgs<CreateAndEditAlarmArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAndEditAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateAndEditAlarmViewModel::class.java]

        binding.endTimePicker.setIs24HourView(true)
        binding.startTimePicker.setIs24HourView(true)

        if (args.isEdit){
            viewModel.setEdit(args.alarm!!)
            setupEditCheckBoxes()
            setupEditTimePickers()
            binding.bAdd.text = getString(R.string.update)
        }

        setupCheckBoxes()

        binding.bCancel.setOnClickListener {
            findNavController().navigate(R.id.action_createAndEditAlarm_to_viewPagerFragment)
        }

        binding.bAdd.setOnClickListener {
            viewModel.addStartTime(binding.startTimePicker.hour, binding.startTimePicker.minute)
            viewModel.addEndTime(binding.endTimePicker.hour, binding.endTimePicker.minute)
            viewModel.insert(requireContext())
            findNavController().navigate(R.id.action_createAndEditAlarm_to_viewPagerFragment)
        }


    }

    private fun onCheckboxClicked(view: View) {
        if (view is CheckBox){
            val checked: Boolean = view.isChecked

            when (view.id){
                R.id.checkboxMonday ->{
                    if (checked)
                        viewModel.addDay(Alarm.Days.MONDAY)
                    else
                        viewModel.removeDay(Alarm.Days.MONDAY)
                }
                R.id.checkboxTuesday ->{
                    if (checked)
                        viewModel.addDay(Alarm.Days.TUESDAY)
                    else
                        viewModel.removeDay(Alarm.Days.TUESDAY)
                }
                R.id.checkboxWednesday ->{
                    if (checked)
                        viewModel.addDay(Alarm.Days.WEDNESDAY)
                    else
                        viewModel.removeDay(Alarm.Days.WEDNESDAY)
                }
                R.id.checkboxThursday ->{
                    if (checked)
                        viewModel.addDay(Alarm.Days.THURSDAY)
                    else
                        viewModel.removeDay(Alarm.Days.THURSDAY)
                }
                R.id.checkboxFriday ->{
                    if (checked)
                        viewModel.addDay(Alarm.Days.FRIDAY)
                    else
                        viewModel.removeDay(Alarm.Days.FRIDAY)
                }
                R.id.checkboxSaturday ->{
                    if (checked)
                        viewModel.addDay(Alarm.Days.SATURDAY)
                    else
                        viewModel.removeDay(Alarm.Days.SATURDAY)
                }
                R.id.checkboxSunday ->{
                    if (checked)
                        viewModel.addDay(Alarm.Days.SUNDAY)
                    else
                        viewModel.removeDay(Alarm.Days.SUNDAY)
                }

            }
        }
    }

    private fun setupEditTimePickers(){
        binding.startTimePicker.hour = TimeConverter.hour(args.alarm!!.start)
        binding.startTimePicker.minute = TimeConverter.minute(args.alarm!!.start)

        binding.endTimePicker.hour = TimeConverter.hour(args.alarm!!.end)
        binding.endTimePicker.minute = TimeConverter.minute(args.alarm!!.end)
    }

    private fun setupEditCheckBoxes(){
        for (day in args.alarm!!.repeat){
            when(day){
                Alarm.Days.MONDAY -> binding.checkboxMonday.isChecked = true
                Alarm.Days.TUESDAY -> binding.checkboxTuesday.isChecked = true
                Alarm.Days.WEDNESDAY -> binding.checkboxWednesday.isChecked = true
                Alarm.Days.THURSDAY -> binding.checkboxThursday.isChecked = true
                Alarm.Days.FRIDAY -> binding.checkboxFriday.isChecked = true
                Alarm.Days.SATURDAY -> binding.checkboxSaturday.isChecked = true
                Alarm.Days.SUNDAY -> binding.checkboxSunday.isChecked = true
            }
        }

        binding.checkboxMonday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxTuesday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxWednesday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxTuesday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxFriday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxSaturday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxSunday.setOnClickListener { onCheckboxClicked(it) }
    }

    private fun setupCheckBoxes(){
        binding.checkboxMonday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxTuesday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxWednesday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxTuesday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxFriday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxSaturday.setOnClickListener { onCheckboxClicked(it) }
        binding.checkboxSunday.setOnClickListener { onCheckboxClicked(it) }
    }

}