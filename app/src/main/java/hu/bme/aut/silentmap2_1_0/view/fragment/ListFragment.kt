package hu.bme.aut.silentmap2_1_0.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import hu.bme.aut.silentmap2_1_0.data.alarm.model.Alarm
import hu.bme.aut.silentmap2_1_0.data.contact.model.Contact
import hu.nemeth.android.silentmap.R
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.bme.aut.silentmap2_1_0.view.callback.UpdateAlarmCallback
import hu.bme.aut.silentmap2_1_0.view.callback.UpdateContactCallback
import hu.bme.aut.silentmap2_1_0.view.recyclerview.AlarmAdapter
import hu.bme.aut.silentmap2_1_0.view.recyclerview.ContactAdapter
import hu.bme.aut.silentmap2_1_0.view.viewmodel.ListViewModel
import hu.nemeth.android.silentmap.view.recyclerview.TerritoryAdapter
import hu.nemeth.android.silentmap.databinding.FragmentListBinding
import hu.nemeth.android.silentmap.logic.converter.ResourceColorConverter
import hu.nemeth.android.silentmap.view.animations.HideFloatButton
import hu.nemeth.android.silentmap.view.callback.ScrollListCallback
import hu.nemeth.android.silentmap.view.callback.UpdateTerritoryCallback
import hu.nemeth.android.silentmap.view.viewpager.ViewPagerFragmentDirections

class ListFragment: Fragment(),
    ScrollListCallback,
    UpdateTerritoryCallback,
    UpdateContactCallback,
    UpdateAlarmCallback{

    private lateinit var territoryAdapter: TerritoryAdapter
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var alarmAdapter: AlarmAdapter
    private lateinit var viewModel: ListViewModel

    private lateinit var binding: FragmentListBinding

    private var clicked = false
    private val rotateOpen: Animation by lazy {AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy {AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(context, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        viewModel.territories.observe(viewLifecycleOwner, { terr ->
            territoryAdapter.submitList(terr)
        })
        viewModel.contacts.observe(viewLifecycleOwner, {cont ->
            contactAdapter.submitList(cont)
        })
        viewModel.alarms.observe(viewLifecycleOwner, {alarm ->
            alarmAdapter.submitList(alarm)
        })


        val gridLayoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        territoryAdapter = TerritoryAdapter(this, ResourceColorConverter::convertDrawable)
        contactAdapter = ContactAdapter(this)
        alarmAdapter = AlarmAdapter(this)

        binding.territoryList.setOnScrollListener(HideFloatButton(binding.fbAdd, this))
        binding.territoryList.adapter = ConcatAdapter(territoryAdapter, contactAdapter, alarmAdapter)
        binding.territoryList.layoutManager = gridLayoutManager

        binding.fbAdd.setOnClickListener {
            onAddButtonClicked()
        }
        binding.fbAddTerr.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_createAndEditTerritory)
        }
        binding.fbAddClock.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_createAndEditAlarm)
        }
        binding.fbAddContact.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_addContactFragment)
        }

    }

    private fun onAddButtonClicked() {
        setButtonVisibility()
        setButtonAnimation()
        setButtonClickable()
        clicked = !clicked
    }

    private fun setButtonClickable() {
        if (!clicked){
            binding.fbAddTerr.isClickable = true
            binding.fbAddContact.isClickable = true
            binding.fbAddClock.isClickable = true
        }
        else{
            binding.fbAddTerr.isClickable = false
            binding.fbAddClock.isClickable = false
            binding.fbAddContact.isClickable = false
        }
    }

    private fun setButtonAnimation() {
        if (!clicked){
            binding.fbAddTerr.startAnimation(fromBottom)
            binding.fbAddContact.startAnimation(fromBottom)
            binding.fbAddClock.startAnimation(fromBottom)
            binding.fbAdd.startAnimation(rotateOpen)
        }
        else{
            binding.fbAddTerr.startAnimation(toBottom)
            binding.fbAddContact.startAnimation(toBottom)
            binding.fbAddClock.startAnimation(toBottom)
            binding.fbAdd.startAnimation(rotateClose)
        }
    }

    private fun setButtonVisibility() {
        if (!clicked){
            binding.fbAddTerr.visibility = View.VISIBLE
            binding.fbAddContact.visibility = View.VISIBLE
            binding.fbAddClock.visibility = View.VISIBLE
        }
        else{
            binding.fbAddTerr.visibility = View.INVISIBLE
            binding.fbAddContact.visibility = View.INVISIBLE
            binding.fbAddClock.visibility = View.INVISIBLE
        }
    }

    override fun onScrollDownCallBack() {
        binding.fbAdd.hide()
        binding.fbAdd.isClickable = false
        if (clicked){
            binding.fbAddTerr.hide()
            binding.fbAddContact.hide()
            binding.fbAddClock.hide()

            binding.fbAddTerr.isClickable = false
            binding.fbAddContact.isClickable = false
            binding.fbAddClock.isClickable = false
        }
    }

    override fun onScrollUpCallBack() {
        binding.fbAdd.show()
        binding.fbAdd.isClickable = true
        if (clicked){
            binding.fbAddTerr.show()
            binding.fbAddContact.show()
            binding.fbAddClock.show()

            binding.fbAddTerr.isClickable = true
            binding.fbAddContact.isClickable = true
            binding.fbAddClock.isClickable = true
        }
    }

    override fun itemClicked(territory: Territory) {
        val action = ViewPagerFragmentDirections.actionViewPagerFragmentToBottomSheetFragment(territory)
        findNavController().navigate(action)
    }

    override fun itemClicked(contact: Contact) {
        val action = ViewPagerFragmentDirections.actionViewPagerFragmentToContactBottomSheetFragment(contact)
        findNavController().navigate(action)
    }

    override fun itemClicked(alarm: Alarm) {
        val action = ViewPagerFragmentDirections.actionViewPagerFragmentToAlarmBottomSheetFragment(alarm)
        findNavController().navigate(action)
    }
}