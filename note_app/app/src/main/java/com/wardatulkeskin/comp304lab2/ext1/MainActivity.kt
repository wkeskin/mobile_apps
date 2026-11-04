package com.wardatulkeskin.comp304lab2.ext1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.wardatulkeskin.comp304lab2.ext1.navigation.NavGraph
import com.wardatulkeskin.comp304lab2.ext1.ui.theme.Comp304lab2ext1Theme
import com.wardatulkeskin.comp304lab2.ext1.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import com.wardatulkeskin.comp304lab2.ext1.di.appModule

/**
 * MainActivity.kt
 *
 * The entry point of the Task Manager application.
 * This activity sets up the dependency injection (DI) container using Koin,
 * initializes the TaskViewModel, and defines the navigation graph within
 * a Material Design 3 themed environment.
 *
 * Responsibilities:
 * - Starts the Koin dependency injection framework.
 * - Injects the shared [TaskViewModel] using Koin.
 * - Sets up the app's composable content and navigation graph.
 * - Applies the global Material 3 theme defined in Comp304lab2ext1Theme.
 *
 * This file represents the "Controller" or entry point that binds
 * together the ViewModel, DI, and Composable UI hierarchy.
 *
 * Author: Wardatul Keskin
 * Course: COMP304 – Assignment 2
 */
class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start Koin dependency injection and load the app module.
        startKoin { modules(appModule) }

        // Set the main UI content of the app.
        setContent {
            // Apply global Material 3 theme
            Comp304lab2ext1Theme {
                // Remember the NavController to manage screen navigation
                val navController = rememberNavController()

                // Setup the navigation graph with the shared ViewModel
                NavGraph(navController, taskViewModel)
            }
        }
    }
}