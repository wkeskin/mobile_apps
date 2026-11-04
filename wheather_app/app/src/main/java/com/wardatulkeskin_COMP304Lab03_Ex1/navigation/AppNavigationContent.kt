package com.wardatulkeskin_COMP304Lab03_Ex1.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Wraps the app's navigation inside a Material3 Scaffold.
 * Adds a top bar. Responsible for responsive layouts if expanded later.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationContent(
    contentType: ContentType = ContentType.SINGLE_PANE,
    navigationType: NavigationType = NavigationType.BOTTOM_NAVIGATION
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Weather Locations") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        AppNavigation(
            modifier = Modifier.padding(innerPadding)
        )
    }
}