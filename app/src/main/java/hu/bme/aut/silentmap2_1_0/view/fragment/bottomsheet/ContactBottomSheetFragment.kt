package hu.bme.aut.silentmap2_1_0.view.fragment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hu.bme.aut.silentmap2_1_0.view.viewmodel.AddContactViewModel
import hu.bme.aut.silentmap2_1_0.view.viewmodel.ContactBottomSheetViewModel
import hu.nemeth.android.silentmap.databinding.FragmentBottomSheetContactBinding

class ContactBottomSheetFragment : BottomSheetDialogFragment(){
    private lateinit var binding: FragmentBottomSheetContactBinding
    private lateinit var viewModel: ContactBottomSheetViewModel
    private val args by navArgs<ContactBottomSheetFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ContactBottomSheetViewModel::class.java]
        binding.bDelete.setOnClickListener {
            viewModel.delete(args.contact)
            dismiss()
        }
    }
}