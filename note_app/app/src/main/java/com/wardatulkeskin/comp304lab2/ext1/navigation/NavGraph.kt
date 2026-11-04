package com.wardatulkeskin.comp304lab2.ext1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wardatulkeskin.comp304lab2.ext1.ui.view.CreateTaskScreen
import com.wardatulkeskin.comp304lab2.ext1.ui.view.HomeScreen
import com.wardatulkeskin.comp304lab2.ext1.ui.view.ViewEditTaskScreen
import com.wardatulkeskin.comp304lab2.ext1.viewmodel.TaskViewModel

/**
 * NavGraph.kt
 *
 * Defines the navigation graph for the Task Manager application using Jetpack Compose Navigation.
 * This file manages screen transitions between Home, Create, and Edit task views.
 *
 * The navigation graph acts as the single source of truth for all app destinations,
 * improving structure and maintainability under the MVVM architecture.
 *
 * Author: Wardatul Keskin
 * Course: COMP304 – Assignment 2
 */
@Composable
fun NavGraph(navController: NavHostController, viewModel: TaskViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        // HomeScreen
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onAddTask = { navController.navigate("create") },
                onTaskClick = { id -> navController.navigate("edit/$id") }
            )
        }

        // Create screen
        composable("create") {
            CreateTaskScreen(viewModel) { navController.popBackStack() }
        }

        // Edit Screen
        composable("edit/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull() ?: return@composable
            ViewEditTaskScreen(
                viewModel = viewModel,
                taskId = taskId,
                onSave = { navController.popBackStack() },
                onDelete = { navController.popBackStack() }
            )
        }
    }
}
