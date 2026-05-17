package com.janaushadhi.finder.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import com.janaushadhi.finder.utils.PlaceSearchUtils
import com.janaushadhi.finder.viewmodel.StoreViewModel
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

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
        setupSimpleSearch() // Simplified search without complex operations
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
            // Handle place selection - simplified
            hideSearchResults()
            etPlaceSearch.setText(place.name)
            etPlaceSearch.clearFocus()
            
            // Move map to selected location
            val latLng = LatLng(place.latitude, place.longitude)
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
            
            // Update location status
            tvLocationStatus.text = place.name
            
            // Update user location in ViewModel (this might trigger store loading)
            viewModel.setUserLocation(place.latitude, place.longitude)
            
            Toast.makeText(
                requireContext(),
                "Location updated to ${place.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
        
        rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = placeSearchAdapter
        }
    }

    private fun setupSimpleSearch() {
        etPlaceSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString()?.trim() ?: ""
                
                if (query.length >= 2) {
                    // Simple synchronous search - no coroutines, no delays
                    performSimpleSearch(query)
                } else {
                    hideSearchResults()
                }
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun performSimpleSearch(query: String) {
        try {
            // Get results directly from PlaceSearchUtils without any async operations
            val results = getSearchResultsSync(query)
            
            if (results.isNotEmpty()) {
                placeSearchAdapter.submitList(results)
                showSearchResults()
            } else {
                hideSearchResults()
            }
        } catch (e: Exception) {
            Log.e("StoresFragment", "Search error", e)
            hideSearchResults()
        }
    }

    private fun getSearchResultsSync(query: String): List<PlaceSearchUtils.PlaceResult> {
        // Simplified synchronous search using predefined cities
        val searchQuery = query.lowercase().trim()
        val results = mutableListOf<PlaceSearchUtils.PlaceResult>()
        
        // Simple city matches
        val cities = mapOf(
            "delhi" to PlaceSearchUtils.PlaceResult("Delhi", 28.6139, 77.2090, "Delhi, India"),
            "mumbai" to PlaceSearchUtils.PlaceResult("Mumbai", 19.0760, 72.8777, "Mumbai, Maharashtra, India"),
            "bangalore" to PlaceSearchUtils.PlaceResult("Bangalore", 12.9716, 77.5946, "Bangalore, Karnataka, India"),
            "chennai" to PlaceSearchUtils.PlaceResult("Chennai", 13.0827, 80.2707, "Chennai, Tamil Nadu, India"),
            "kolkata" to PlaceSearchUtils.PlaceResult("Kolkata", 22.5726, 88.3639, "Kolkata, West Bengal, India"),
            "hyderabad" to PlaceSearchUtils.PlaceResult("Hyderabad", 17.3850, 78.4867, "Hyderabad, Telangana, India"),
            "pune" to PlaceSearchUtils.PlaceResult("Pune", 18.5204, 73.8567, "Pune, Maharashtra, India"),
            "ahmedabad" to PlaceSearchUtils.PlaceResult("Ahmedabad", 23.0225, 72.5714, "Ahmedabad, Gujarat, India"),
            "jaipur" to PlaceSearchUtils.PlaceResult("Jaipur", 26.9124, 75.7873, "Jaipur, Rajasthan, India"),
            "surat" to PlaceSearchUtils.PlaceResult("Surat", 21.1702, 72.8311, "Surat, Gujarat, India")
        )
        
        for ((cityName, placeResult) in cities) {
            if (cityName.contains(searchQuery) || placeResult.name.lowercase().contains(searchQuery)) {
                results.add(placeResult)
                if (results.size >= 5) break
            }
        }
        
        return results
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
                    googleMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(userLocation!!, 12f)
                    )
                    tvLocationStatus.text = "Current Location"
                    
                    // Only set user location in ViewModel after map is ready
                    viewModel.setUserLocation(it.latitude, it.longitude)
                } ?: run {
                    // Default to Delhi if location not available
                    val defaultLocation = LatLng(28.6139, 77.2090)
                    googleMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f)
                    )
                    tvLocationStatus.text = "Delhi (Default Location)"
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
        // Only observe nearby stores - simplified
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

    private fun showSearchResults() {
        rvSearchResults.visibility = View.VISIBLE
    }

    private fun hideSearchResults() {
        rvSearchResults.visibility = View.GONE
    }
}
