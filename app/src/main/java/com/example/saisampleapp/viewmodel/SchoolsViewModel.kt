package com.example.saisampleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saisampleapp.data.model.School
import com.example.saisampleapp.data.model.repository.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val schoolRepository: SchoolRepository
) :
    ViewModel() {

    private val _schools = MutableLiveData<List<School>>()
    val schools: LiveData<List<School>> get() = _schools

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _filteredSchools = MutableLiveData<List<School>>()
    val filteredSchools: LiveData<List<School>> = _filteredSchools

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchSchools()

    }

    fun updateSearchQuery(query: String) {
        _searchQuery.postValue(query)
        val currentSchools = _schools.value ?: emptyList()
        val filteredList = if (query.isEmpty()) {
            currentSchools
        } else {
            currentSchools.filter { school ->
                school.school_name.contains(query, ignoreCase = true) || school.school_email.contains(query, ignoreCase = true)
            }
        }
        _filteredSchools.postValue(filteredList)
    }

    private fun fetchSchools() {
        viewModelScope.launch {
            try {
                _schools.value = schoolRepository.getSchools()
                _filteredSchools.value = schoolRepository.getSchools()
            } catch (_:Exception){

            } finally {
                _isLoading.value = false
            }
        }
    }
}