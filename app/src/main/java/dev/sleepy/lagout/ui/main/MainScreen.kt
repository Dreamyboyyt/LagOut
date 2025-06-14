package dev.sleepy.lagout.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.sleepy.lagout.ui.dashboard.DashboardScreen
import dev.sleepy.lagout.ui.tools.ToolsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Dashboard", "Profiles", "Tools", "History")

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index
                                    navController.navigate(title) },
                        text = { Text(title) }
                    )
                }
            }
        }
    ) {\n        paddingValues ->
        NavHost(navController = navController, startDestination = tabs[0], modifier = Modifier.padding(paddingValues)) {
            composable(tabs[0]) {
                DashboardScreen()
            }
            composable(tabs[1]) {
                ProfilesScreen()
            }
            composable(tabs[2]) {
                ToolsScreen()
            }
            composable(tabs[3]) {
                Text("History Screen - Coming Soon!") // Placeholder for History Screen
            }
        }
    }
}


