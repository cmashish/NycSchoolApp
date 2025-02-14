package com.example.saisampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saisampleapp.screens.SchoolDetailScreen
import com.example.saisampleapp.ui.theme.SaiSampleAppTheme
import com.example.saisampleapp.screens.SchoolList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaiSampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "school_list") {
        composable("school_list") {
            SchoolList(onSchoolSelected = { dbn ->
                navController.navigate("school_details/$dbn")
            })
        }
        composable("school_details/{dbn}") { backStackEntry ->
            val dbn = backStackEntry.arguments?.getString("dbn") ?: ""
            SchoolDetailScreen(dbn = dbn)
        }
    }
}

/*@Composable
fun SchoolListScreen(
    viewModel: SchoolsViewModel = hiltViewModel(),
    onSchoolSelected: (String) -> Unit
) {
    val schools by viewModel.schools.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)

    if (isLoading) {
        // Display loading indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(schools) { school ->
                SchoolListItem(school = school, onClick = { onSchoolSelected(school.dbn) })
            }
        }
    }

}

@Composable
fun SchoolListItem(school: School, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column {
            Text(
                text = "School Name : ${school.school_name}",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "School Email : ${school.school_email}" ?: "No email available",
                style = MaterialTheme.typography.bodySmall
            )
            Button(onClick = { onClick() }) {
                Text("Show Details")
            }
        }
    }
}

@Composable
fun SatScoreScreen(viewModel: SchoolDetailsViewModel = hiltViewModel(), dbn: String) {
    val satScore by viewModel.satScore.observeAsState(null)
    val isLoading by viewModel.isLoading.observeAsState(true)
    LaunchedEffect(dbn) {
        viewModel.fetchSATScore(dbn)
    }

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

}*/
