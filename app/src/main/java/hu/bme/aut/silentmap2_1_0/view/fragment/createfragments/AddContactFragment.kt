package hu.bme.aut.silentmap2_1_0.view.fragment.createfragments

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact
import hu.bme.aut.silentmap2_1_0.view.callback.UpdateContactCallback
import hu.bme.aut.silentmap2_1_0.view.recyclerview.ContactAdapter
import hu.bme.aut.silentmap2_1_0.view.viewmodel.AddContactViewModel
import hu.nemeth.android.silentmap.R
import hu.nemeth.android.silentmap.databinding.FragmentAddContactBinding
import permissions.dispatcher.*

@RuntimePermissions
class AddContactFragment : Fragment(), UpdateContactCallback {
    private lateinit var binding: FragmentAddContactBinding
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var viewModel: AddContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AddContactViewModel::class.java]
        loadContactsWithPermissionCheck()


        contactAdapter = ContactAdapter(this)
        contactAdapter.submitList(viewModel.phoneContacts)

        val gridLayoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.contactList.adapter = contactAdapter
        binding.contactList.layoutManager = gridLayoutManager
    }

    override fun itemClicked(contact: Contact) {
        viewModel.insert(contact)
        findNavController().navigate(R.id.action_addContactFragment_to_viewPagerFragment)
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(
        Manifest.permission.READ_CONTACTS
    )
    fun loadContacts(){
        viewModel.loadPhoneContacts(requireActivity().contentResolver)
        viewModel.savedContacts.observe(viewLifecycleOwner, Observer {
            viewModel.removeSaved()
        })
    }


    @OnPermissionDenied(
        Manifest.permission.READ_CONTACTS
    )
    fun locationPermissionDenied() {
        Toast.makeText(context, "Permission Denied...", Toast.LENGTH_SHORT).show()
    }


    @OnShowRationale(
        Manifest.permission.READ_CONTACTS
    )
    fun showRationaleForLocation(request: PermissionRequest) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(activity?.title)
                .setMessage("Permission is needed for load your contacts")
                .setCancelable(false)
                .setPositiveButton("Proceed") { _, _ -> request.proceed() }
                .setNegativeButton("Exit") { _, _ -> request.cancel() }
                .create()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}