package com.janaushadhi.finder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.janaushadhi.finder.data.database.AppDatabase
import com.janaushadhi.finder.data.model.Store
import com.janaushadhi.finder.data.repository.StoreRepository
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

    init {
        val database = AppDatabase.getDatabase(application)
        repository = StoreRepository(database.storeDao())
        allStores = repository.getAllStores().asLiveData()
    }

    fun setUserLocation(lat: Double, lng: Double) {
        _userLocation.value = Pair(lat, lng)
        loadNearbyStores(lat, lng)
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
}
