package com.janaushadhi.finder.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.janaushadhi.finder.R
import com.janaushadhi.finder.utils.LocationUtils
import com.janaushadhi.finder.viewmodel.StoreViewModel

class StoresFragment : Fragment(), OnMapReadyCallback {

    private lateinit var viewModel: StoreViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null
    private var userLocation: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[StoreViewModel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        observeViewModel()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.apply {
            isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true
        }

        getUserLocation()
    }

    private fun getUserLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap?.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                    viewModel.setUserLocation(it.latitude, it.longitude)
                    googleMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(userLocation!!, 12f)
                    )
                } ?: run {
                    // Default to Delhi if location not available
                    val defaultLocation = LatLng(28.6139, 77.2090)
                    googleMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f)
                    )
                    viewModel.setUserLocation(28.6139, 77.2090)
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                R.string.location_permission_required,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeViewModel() {
        viewModel.nearbyStores.observe(viewLifecycleOwner) { stores ->
            googleMap?.clear()
            stores.forEach { store ->
                val position = LatLng(store.latitude, store.longitude)
                val marker = googleMap?.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(store.name)
                        .snippet(
                            "${LocationUtils.formatDistance(store.distanceKm)} • " +
                                    if (store.isOpenNow) "Open Now" else "Closed"
                        )
                )
                marker?.tag = store
            }
        }
    }
}
