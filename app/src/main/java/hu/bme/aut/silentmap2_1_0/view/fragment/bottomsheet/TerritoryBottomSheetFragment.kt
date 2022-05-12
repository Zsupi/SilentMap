package hu.bme.aut.silentmap2_1_0.view.fragment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hu.nemeth.android.silentmap.databinding.FragmentBottomSheetBinding
import hu.nemeth.android.silentmap.view.viewmodel.TerritoryBottomSheetViewModel

class TerritoryBottomSheetFragment : BottomSheetDialogFragment(){

    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var viewModel: TerritoryBottomSheetViewModel
    private val args by navArgs<TerritoryBottomSheetFragmentArgs>()

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
        viewModel = ViewModelProvider(this)[TerritoryBottomSheetViewModel::class.java]

        binding.bDelete.setOnClickListener {
            viewModel.delete(args.editTerritory)
            dismiss()
        }

        binding.bEdit.setOnClickListener {
            val action = TerritoryBottomSheetFragmentDirections.actionBottomSheetFragmentToCreateAndEditTerritory().setEditTerritory(args.editTerritory)
            findNavController().navigate(action)
        }
    }
}