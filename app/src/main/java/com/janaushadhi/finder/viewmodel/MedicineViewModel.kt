package com.janaushadhi.finder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.janaushadhi.finder.data.database.AppDatabase
import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.data.repository.MedicineRepository
import kotlinx.coroutines.launch

class MedicineViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MedicineRepository
    val allMedicines: LiveData<List<Medicine>>
    val favoriteMedicines: LiveData<List<Medicine>>

    private val _searchResults = MutableLiveData<List<Medicine>>()
    val searchResults: LiveData<List<Medicine>> = _searchResults

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean> = _isSearching

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MedicineRepository(database.medicineDao())
        allMedicines = repository.getAllMedicines().asLiveData()
        favoriteMedicines = repository.getFavoriteMedicines().asLiveData()
        loadCategories()
    }

    fun searchMedicines(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        _isSearching.value = true
        viewModelScope.launch {
            try {
                val results = repository.searchMedicines(query)
                _searchResults.postValue(results)
            } catch (e: Exception) {
                _searchResults.postValue(emptyList())
            } finally {
                _isSearching.postValue(false)
            }
        }
    }

    fun clearSearch() {
        _searchResults.value = emptyList()
    }

    fun toggleFavorite(medicine: Medicine) {
        viewModelScope.launch {
            repository.toggleFavorite(medicine)
        }
    }

    fun getMedicinesByCategory(category: String): LiveData<List<Medicine>> {
        return repository.getMedicinesByCategory(category).asLiveData()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val cats = repository.getAllCategories()
            _categories.postValue(cats)
        }
    }
}
