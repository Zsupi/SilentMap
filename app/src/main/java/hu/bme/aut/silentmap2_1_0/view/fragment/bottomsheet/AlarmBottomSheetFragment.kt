package hu.bme.aut.silentmap2_1_0.view.fragment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hu.bme.aut.silentmap2_1_0.view.viewmodel.AlarmBottomSheetViewModel
import hu.nemeth.android.silentmap.databinding.FragmentBottomSheetBinding

class AlarmBottomSheetFragment: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var viewModel: AlarmBottomSheetViewModel
    private val args by navArgs<AlarmBottomSheetFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AlarmBottomSheetViewModel::class.java]

        binding.bDelete.setOnClickListener {
            viewModel.delete(args.alarm, requireContext())
            dismiss()
        }

        binding.bEdit.setOnClickListener {
            val action = AlarmBottomSheetFragmentDirections.actionAlarmBottomSheetFragmentToCreateAndEditAlarm().setAlarm(args.alarm)
            findNavController().navigate(action)
        }
    }

}