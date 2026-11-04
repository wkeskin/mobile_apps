package com.wardatulkeskin.comp304lab2.ext1.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.wardatulkeskin.comp304lab2.ext1.ui.components.TaskItem
import com.wardatulkeskin.comp304lab2.ext1.viewmodel.TaskViewModel
import com.wardatulkeskin.comp304lab2.ext1.utils.rememberWindowSizeClass

/**
 * HomeScreen.kt
 *
 * Displays a responsive list of all tasks and provides navigation to create or edit tasks.
 * This is the main screen (Home) of the Task Manager app.
 *
 * Features:
 * - Uses LazyColumn for efficient list rendering.
 * - Adapts layout dynamically using WindowSizeClass for phones, tablets, and foldables.
 * - Includes a FloatingActionButton (FAB) for adding new tasks.
 * - Integrates accessibility features through semantics and content descriptions.
 * - Follows Material Design 3 guidelines.
 *
 * Represents the "View" layer in the MVVM architecture.
 *
 * Author: Wardatul Keskin
 * Course: COMP304 – Assignment 2
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TaskViewModel,
    onAddTask: () -> Unit,
    onTaskClick: (Int) -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val windowSize = rememberWindowSizeClass()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTask,
                modifier = Modifier
                    .semantics { contentDescription = "Add new task button" }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Task"
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Task Manager") },
                modifier = Modifier.semantics { contentDescription = "Top App Bar" }
            )
        }
    ) { paddingValues ->

        when (windowSize.widthSizeClass) {
            // 📱 Compact - phones
            WindowWidthSizeClass.Compact -> {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { contentDescription = "Compact Task List" }
                ) {
                    items(tasks) { task ->
                        TaskItem(task = task) { onTaskClick(task.id) }
                    }
                }
            }

            // 💻 Medium/Expanded - tablets or foldables
            WindowWidthSizeClass.Medium,
            WindowWidthSizeClass.Expanded -> {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .semantics { contentDescription = "Two-column Task Layout" },
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Left column
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(tasks.filterIndexed { index, _ -> index % 2 == 0 }) { task ->
                            TaskItem(task = task) { onTaskClick(task.id) }
                        }
                    }

                    // Right column
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(tasks.filterIndexed { index, _ -> index % 2 != 0 }) { task ->
                            TaskItem(task = task) { onTaskClick(task.id) }
                        }
                    }
                }
            }
        }
    }
}