package com.wardatulkeskin.comp304lab2.ext1.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.wardatulkeskin.comp304lab2.ext1.data.model.Task
import com.wardatulkeskin.comp304lab2.ext1.viewmodel.TaskViewModel

/**
 * CreateTaskScreen.kt
 *
 * A composable screen that allows users to create a new task.
 * The user can input a task title, description, and due date,
 * then save the task, which is added to the list via the ViewModel.
 *
 * Features:
 * - Input validation for date format (dd/mm/yyyy)
 * - Scrollable layout for smaller devices
 * - Accessibility support with semantic labels
 * - Uses Material Design 3 components for consistency
 *
 * This screen represents part of the "View" layer in the MVVM architecture.
 *
 * Author: Wardatul Keskin
 * Course: COMP304 – Assignment 2
 */

@Composable
fun CreateTaskScreen(viewModel: TaskViewModel, onSave: () -> Unit) {
    // Local states for inputs
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var dateError by remember { mutableStateOf(false) }

    // Scrollable layout for smaller screens
    val scrollState = rememberScrollState()

    Column(modifier =Modifier.fillMaxSize(0.98f).verticalScroll(scrollState).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        // Title field
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title")}, modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Task Title Input" }, singleLine = true)
        // Description field
        OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description")}, modifier = Modifier.fillMaxWidth().heightIn(150.dp).semantics { contentDescription = "Task Description Input" },maxLines = Int.MAX_VALUE)
        // Due date field
        OutlinedTextField(
            value = date,
            onValueChange = {
                date = it
                dateError = !it.matches(Regex("""\d{2}/\d{2}/\d{4}"""))
            },
            label = { Text("Due Date (dd/mm/yyyy)") },
            isError = dateError,
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .semantics { contentDescription = "Task Due Date Input" },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (dateError && date.isNotBlank()) {
            Text(
                text = "Please use the format dd/mm/yyyy",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start).padding(top = 2.dp, start = 8.dp)
            )
        }
        Spacer(Modifier.height(16.dp))

        //Save Task Button
        Button(
            onClick = {
                val task = Task(
                    id = (0..100000).random(),
                    title = title.trim(),
                    description = desc.trim(),
                    dueDate = date.trim()
                )
                viewModel.addTask(task)
                onSave()
            },
            enabled = title.isNotBlank() && date.matches(Regex("""\d{2}/\d{2}/\d{4}""")),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.Start)
                .semantics { contentDescription = "Save Task Button" },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Save Task")
        }
    }
}