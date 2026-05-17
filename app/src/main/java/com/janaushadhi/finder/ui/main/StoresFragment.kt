package com.janaushadhi.finder.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.janaushadhi.finder.R
import com.janaushadhi.finder.adapter.PlaceSearchAdapter
import com.janaushadhi.finder.utils.LocationUtils
import com.janaushadhi.finder.viewmodel.StoreViewModel
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StoresFragment : Fragment(), OnMapReadyCallback {

    private lateinit var viewModel: StoreViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var placeSearchAdapter: PlaceSearchAdapter
    
    private var googleMap: GoogleMap? = null
    private var userLocation: LatLng? = null
    
    // UI Components
    private lateinit var etPlaceSearch: TextInputEditText
    private lateinit var rvSearchResults: RecyclerView
    private lateinit var tvLocationStatus: TextView
    private lateinit var fabMyLocation: FloatingActionButton
    private lateinit var progressSearch: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupViewModel()
        setupRecyclerView()
        setupSearchFunctionality()
        setupMapFragment()
        observeViewModel()
    }

    private fun initializeViews(view: View) {
        etPlaceSearch = view.findViewById(R.id.et_place_search)
        rvSearchResults = view.findViewById(R.id.rv_search_results)
        tvLocationStatus = view.findViewById(R.id.tv_location_status)
        fabMyLocation = view.findViewById(R.id.fab_my_location)
        progressSearch = view.findViewById(R.id.progress_search)
        
        fabMyLocation.setOnClickListener {
            getUserLocation()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[StoreViewModel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun setupRecyclerView() {
        placeSearchAdapter = PlaceSearchAdapter { place ->
            // Handle place selection
            viewModel.selectPlace(place)
            hideSearchResults()
            etPlaceSearch.setText(place.name)
            etPlaceSearch.clearFocus()
            
            // Move map to selected location
            val latLng = LatLng(place.latitude, place.longitude)
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
            
            Toast.makeText(
                requireContext(),
                getString(R.string.location_updated, place.name),
                Toast.LENGTH_SHORT
            ).show()
        }
        
        rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = placeSearchAdapter
        }
    }

    private fun setupSearchFunctionality() {
        var searchJob: Job? = null
        
        etPlaceSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString()?.trim() ?: ""
                
                // Cancel previous search job
                searchJob?.cancel()
                
                if (query.length >= 2) {
                    // Debounce search by 300ms
                    searchJob = lifecycleScope.launch {
                        delay(300)
                        if (isActive) {
                            viewModel.searchPlaces(query)
                        }
                    }
                } else {
                    hideSearchResults()
                }
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })

        etPlaceSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || 
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                
                val query = etPlaceSearch.text?.toString()?.trim()
                if (!query.isNullOrEmpty()) {
                    searchJob?.cancel()
                    viewModel.searchPlaces(query)
                }
                true
            } else {
                false
            }
        }

        etPlaceSearch.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                hideSearchResults()
            }
        }
    }

    private fun setupMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.apply {
            isZoomControlsEnabled = true
            isMyLocationButtonEnabled = false // We have our own FAB
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
            tvLocationStatus.text = getString(R.string.getting_location)
            
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
                    tvLocationStatus.text = "Delhi (Default Location)"
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
        // Observe nearby stores
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

        // Observe search results
        viewModel.searchResults.observe(viewLifecycleOwner) { results ->
            if (results.isNotEmpty()) {
                placeSearchAdapter.submitList(results)
                showSearchResults()
            } else {
                hideSearchResults()
            }
        }

        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe search errors
        viewModel.searchError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        // Observe current location name
        viewModel.currentLocationName.observe(viewLifecycleOwner) { locationName ->
            tvLocationStatus.text = locationName
        }
    }

    private fun showSearchResults() {
        rvSearchResults.visibility = View.VISIBLE
    }

    private fun hideSearchResults() {
        rvSearchResults.visibility = View.GONE
        viewModel.clearSearchResults()
    }
}
