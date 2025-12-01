package com.example.quizito.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizito.ui.screens.ViewModel.ThemeViewModel
import com.example.quizito.ui.screens.ThemeCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeScreen(
    viewModel: ThemeViewModel = hiltViewModel(),
    onThemeClick: (String) -> Unit,
    onLogoutClick: () -> Unit,
    onMyScoresClick: () -> Unit,
    onAllScoresClick: () -> Unit
) {
    val themes = viewModel.filteredThemes()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menu",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                Divider()

                NavigationDrawerItem(
                    label = { Text("Déconnexion") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onLogoutClick()
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Voir mes scores") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onMyScoresClick()
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Meilleurs scores par thème") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onAllScoresClick()
                    }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 🔍 Barre de recherche avec bouton menu
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.searchQuery = it },
                placeholder = { Text("Rechercher un thème...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true,
                leadingIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            )

            // 📋 Liste des thèmes
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(themes, key = { it.id }) { theme ->
                    ThemeCard(theme) { onThemeClick(theme.id) }
                }
            }
        }
    }
}