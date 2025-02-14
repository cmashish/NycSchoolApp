package com.example.saisampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saisampleapp.data.model.SATScores
import com.example.saisampleapp.data.model.repository.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val schoolRepository: SchoolRepository
) : ViewModel() {

    private val _satScore = MutableLiveData<SATScores?>()
    val satScore: LiveData<SATScores?> get() = _satScore

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchSATScore(dbn: String) {
        viewModelScope.launch {
            try {
                val scores = schoolRepository.getSatScores(dbn)
                _satScore.value = if (scores.isNotEmpty()) scores.first() else null// Assuming one entry per school
            } catch (e: Exception) {
                // Handle the error
                _satScore.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}
