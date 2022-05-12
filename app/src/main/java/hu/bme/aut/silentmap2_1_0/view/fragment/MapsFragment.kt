package hu.nemeth.android.silentmap.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import hu.nemeth.android.silentmap.R
import hu.nemeth.android.silentmap.databinding.FragmentMapsBinding
import hu.nemeth.android.silentmap.view.callback.PageChangeCallback
import hu.nemeth.android.silentmap.view.viewmodel.MapViewModel
import permissions.dispatcher.NeedsPermission


class MapsFragment() : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: MapViewModel

    private var isMapReady = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.bBack.setOnClickListener {
            //changePageChangeCallback.changePage(0)
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

        viewModel.getTerritories().observe(viewLifecycleOwner, Observer{
            updateMap()
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            updateMap()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true


        val pref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        if  (pref.getBoolean("NightMode", true)){
            val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.darkmapstyle)
            mMap.setMapStyle(mapStyleOptions)
        }

        mMap.setOnMapClickListener {
            val cameraPosition = CameraPosition.Builder().target(it).zoom(1000.0f).build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            mMap.animateCamera(cameraUpdate)
        }
        isMapReady = true
    }

    private fun updateMap(){
        if (isMapReady){
            mMap.clear()
            for (circle in viewModel.getCircleOptions(context)){
                mMap.addCircle(circle)
            }
            for (marker in viewModel.getMarkerOptions()){
                mMap.addMarker(marker)
            }
        }
    }
}