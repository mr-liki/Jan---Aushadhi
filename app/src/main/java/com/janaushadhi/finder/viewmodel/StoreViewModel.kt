package com.janaushadhi.finder.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.janaushadhi.finder.data.database.AppDatabase
import com.janaushadhi.finder.data.model.Store
import com.janaushadhi.finder.data.repository.StoreRepository
import com.janaushadhi.finder.utils.PlaceSearchUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StoreViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StoreRepository
    val allStores: LiveData<List<Store>>

    private val _nearbyStores = MutableLiveData<List<Store>>()
    val nearbyStores: LiveData<List<Store>> = _nearbyStores

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userLocation = MutableLiveData<Pair<Double, Double>>()
    val userLocation: LiveData<Pair<Double, Double>> = _userLocation

    private val _searchResults = MutableLiveData<List<PlaceSearchUtils.PlaceResult>>()
    val searchResults: LiveData<List<PlaceSearchUtils.PlaceResult>> = _searchResults

    private val _searchError = MutableLiveData<String?>()
    val searchError: LiveData<String?> = _searchError

    private val _currentLocationName = MutableLiveData<String>()
    val currentLocationName: LiveData<String> = _currentLocationName

    private var searchJob: Job? = null

    init {
        val database = AppDatabase.getDatabase(application)
        repository = StoreRepository(database.storeDao())
        allStores = repository.getAllStores().asLiveData()
    }

    fun setUserLocation(lat: Double, lng: Double) {
        _userLocation.value = Pair(lat, lng)
        _currentLocationName.value = "Location Updated"
        
        // Delay store loading to prevent ANR during search
        viewModelScope.launch {
            try {
                kotlinx.coroutines.delay(1000) // Give UI time to settle
                loadNearbyStores(lat, lng)
            } catch (e: Exception) {
                Log.e("StoreViewModel", "Error loading stores", e)
            }
        }
    }

    fun loadNearbyStores(lat: Double, lng: Double, radiusKm: Double = 10.0) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val stores = repository.getStoresNearby(lat, lng, radiusKm)
                _nearbyStores.postValue(stores)
            } catch (e: Exception) {
                _nearbyStores.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun loadAllStoresWithDistance(lat: Double, lng: Double) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val stores = repository.getAllStoresWithDistance(lat, lng)
                _nearbyStores.postValue(stores)
            } catch (e: Exception) {
                _nearbyStores.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun searchPlaces(query: String) {
        if (query.length < 2) {
            _searchResults.value = emptyList()
            return
        }

        // Cancel any existing search job
        searchJob?.cancel()
        
        _isLoading.value = true
        _searchError.value = null
        
        searchJob = viewModelScope.launch {
            try {
                val results = PlaceSearchUtils.searchPlaces(getApplication(), query, 5)
                
                if (isActive) { // Check if coroutine is still active
                    _searchResults.postValue(results)
                    
                    if (results.isEmpty()) {
                        _searchError.postValue("No places found for '$query'")
                    }
                }
            } catch (e: Exception) {
                if (isActive) {
                    Log.e("StoreViewModel", "Search failed for query: $query", e)
                    _searchError.postValue("Search failed. Please try again.")
                    _searchResults.postValue(emptyList())
                }
            } finally {
                if (isActive) {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun selectPlace(place: PlaceSearchUtils.PlaceResult) {
        setUserLocation(place.latitude, place.longitude)
        _currentLocationName.value = place.name
        _searchResults.value = emptyList() // Clear search results
    }

    fun clearSearchResults() {
        _searchResults.value = emptyList()
        _searchError.value = null
    }

    fun getPopularCitySuggestions(query: String): List<String> {
        return PlaceSearchUtils.filterPopularCities(query)
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}
