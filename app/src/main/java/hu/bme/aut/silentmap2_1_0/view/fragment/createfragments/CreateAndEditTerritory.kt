package hu.nemeth.android.silentmap.view.fragment.createfragments

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jem.rubberpicker.RubberSeekBar
import hu.bme.aut.silentmap2_1_0.data.territory.model.Territory
import hu.nemeth.android.silentmap.R
import hu.nemeth.android.silentmap.databinding.FragmentCreateTerritoryBinding
import hu.bme.aut.silentmap2_1_0.view.viewmodel.CreateAndEditViewModel
import permissions.dispatcher.NeedsPermission

class CreateAndEditTerritory(): Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentCreateTerritoryBinding
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: CreateAndEditViewModel

    private val args by navArgs<CreateAndEditTerritoryArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTerritoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateAndEditViewModel::class.java]
        if (args.isEdit){
            viewModel.setEditTerritory(args.editTerritory!!)
            setupEdit()
        }

        binding.bottomSheetCreate.sliderSize.setMax(1000)
        binding.bottomSheetCreate.sliderSize.setMin(100)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        BottomSheetBehavior.from(binding.bottomSheetCreate.bottomsheet).apply {
            peekHeight = 130
        }
        binding.bottomSheetCreate.bAdd.setOnClickListener {
            viewModel.insertOrUpdate()
            findNavController().navigate(R.id.action_createAndEditTerritory_to_viewPagerFragment2)
        }
        binding.bottomSheetCreate.bCancel.setOnClickListener {
            findNavController().navigate(R.id.action_createAndEditTerritory_to_viewPagerFragment2)
        }
        binding.bottomSheetCreate.sliderSize.setOnRubberSeekBarChangeListener(object : RubberSeekBar.OnRubberSeekBarChangeListener{
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                viewModel.updateRadius(seekBar.getCurrentValue().toDouble())
                updateTerritory()
            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })
        binding.bottomSheetCreate.etTitle.addTextChangedListener {
            viewModel.updateTitle(binding.bottomSheetCreate.etTitle.text.toString())
        }
        binding.bottomSheetCreate.grColorPicker.setOnCheckedChangeListener{ _, checkedId ->
            val selected: RadioButton = view.findViewById(checkedId)
            viewModel.updateColor(selected)
            updateTerritory()
        }

        binding.searchView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val location = binding.searchView.query.toString()
                if (location != ""){
                    val geocoder = Geocoder(activity)
                    val address = geocoder.getFromLocationName(location, 1)
                    val latLng = LatLng(address[0].latitude, address[0].longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 100000.0f))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION)
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.darkmapstyle)
        mMap.setMapStyle(mapStyleOptions)

        mMap.setOnMapClickListener {
            val cameraPosition = CameraPosition.Builder().target(it).zoom(100000.0f).build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            mMap.animateCamera(cameraUpdate)
            viewModel.updatePosition(it.latitude, it.longitude)
            updateTerritory()
        }
        if (viewModel.isEdit){
            mMap.addCircle(viewModel.getOldCircle())
            mMap.addMarker(viewModel.getOldMarker())
        }
    }

    private fun updateTerritory(){
        if (viewModel.isDrawable()){
            mMap.clear()
            mMap.addCircle(viewModel.getCircle(context))
            mMap.addMarker(viewModel.getMarker())
            if (viewModel.isEdit){
                mMap.addCircle(viewModel.getOldCircle())
                mMap.addMarker(viewModel.getOldMarker())
            }
        }
    }

    private fun setupEdit(){
        val territory:Territory? = args.editTerritory
        when(territory!!.color){
            Territory.TerrColor.BLUE -> binding.bottomSheetCreate.rbBlue.isChecked = true
            Territory.TerrColor.RED -> binding.bottomSheetCreate.rbRed.isChecked = true
            Territory.TerrColor.GREEN -> binding.bottomSheetCreate.rbGreen.isChecked = true
            Territory.TerrColor.WHITE -> binding.bottomSheetCreate.rbWhite.isChecked = true
            Territory.TerrColor.YELLOW -> binding.bottomSheetCreate.rbOrange.isChecked = true
        }

        binding.bottomSheetCreate.etTitle.setText(territory.title)
        binding.bottomSheetCreate.bAdd.text = getString(R.string.update)
        binding.bottomSheetCreate.sliderSize.setCurrentValue(territory.radius.toInt())
    }
}