package com.example.saisampleapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.saisampleapp.data.model.School
import com.example.saisampleapp.viewmodel.SchoolsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolList(
    viewModel: SchoolsViewModel = hiltViewModel(),
    onSchoolSelected: (String) -> Unit
) {

    val schools by viewModel.schools.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)
    val filteredSchools by viewModel.filteredSchools.observeAsState(emptyList())
    val searchQuery by viewModel.searchQuery.observeAsState()

    Scaffold(
        topBar = {
            Column {
                androidx.compose.material.TopAppBar(
                    title = { Text("NYC Schools") },
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                )
                SearchBar(
                    query = searchQuery ?: "",
                    onQueryChange = { viewModel.updateSearchQuery(it) }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Surface(modifier = Modifier.fillMaxSize()) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.wrapContentSize()
                        )
                    }

                    schools.isEmpty() -> {
                        Text(text = "No Data Found", modifier = Modifier.wrapContentSize())
                    }

                    filteredSchools.isEmpty() -> {
                        Text(text = "No Data Found", modifier = Modifier.wrapContentSize())
                    }

                    else -> {
                        LazyColumn {
                            items(filteredSchools) { school ->
                                SchoolListItem(
                                    school = school,
                                    onClick = { onSchoolSelected(school.dbn) })
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun SchoolListItem(school: School, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "School Name : ${school.school_name}",
                style = MaterialTheme.typography.titleMedium
            )

            Row(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "School Email : ${school.school_email}",
                    style = MaterialTheme.typography.bodySmall, overflow = TextOverflow.Clip, modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick() },
                    Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.End
                ) {
                    Button(onClick = { onClick() }) {
                        Text("View")
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("Search schools...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black
        )
    )
}
