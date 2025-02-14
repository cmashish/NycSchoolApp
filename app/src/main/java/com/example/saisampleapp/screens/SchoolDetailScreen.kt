package com.example.saisampleapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.saisampleapp.SchoolDetailsViewModel

@Composable
fun SchoolDetailScreen(viewModel: SchoolDetailsViewModel = hiltViewModel(), dbn: String) {

    val satScore by viewModel.satScore.observeAsState(null)
    val isLoading by viewModel.isLoading.observeAsState(true)
    LaunchedEffect(dbn) {
        viewModel.fetchSATScore(dbn)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
        // Display loading indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            satScore?.let { score ->
                Text("Critical Reading Avg Score: ${score.sat_critical_reading_avg_score ?: "N/A"}")
                Text("Math Avg Score: ${score.sat_math_avg_score ?: "N/A"}")
                Text("Writing Avg Score: ${score.sat_writing_avg_score ?: "N/A"}")
            } ?: run {
                Text("No Data found")
            }
        }
    }

    }
}