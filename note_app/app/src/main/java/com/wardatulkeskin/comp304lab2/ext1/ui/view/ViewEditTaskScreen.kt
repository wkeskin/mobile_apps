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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wardatulkeskin.comp304lab2.ext1.data.model.Task
import com.wardatulkeskin.comp304lab2.ext1.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * ViewEditTaskScreen.kt
 *
 * Allows users to view, edit, and delete a selected task.
 * Features:
 * - Validates date input.
 * - Updates or deletes a task using ViewModel (MVVM pattern).
 * - Checkbox to mark task as completed.
 * - Confirmation dialog before deletion.
 * - Dynamic due-date color (red if overdue, green if upcoming).
 *
 * Author: Wardatul Keskin
 * Course: COMP304 - Assignment 2
 */
@Composable
fun ViewEditTaskScreen(
    viewModel: TaskViewModel,
    taskId: Int,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    // Retrieve the task to edit
    val task = viewModel.tasks.collectAsState().value.find { it.id == taskId }

    // Local states for editing
    var title by remember { mutableStateOf(task?.title ?: "") }
    var desc by remember { mutableStateOf(task?.description ?: "") }
    var date by remember { mutableStateOf(task?.dueDate ?: "") }
    var completed by remember { mutableStateOf(task?.isCompleted ?: false) }
    var dateError by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    // Date comparison helper
    fun isOverdue(dateString: String): Boolean {
        return try {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dueDate = format.parse(dateString)
            val today = Calendar.getInstance().time
            dueDate != null && dueDate.before(today)
        } catch (e: Exception) {
            false
        }
    }

    if (task == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Task not found.")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Task",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(24.dp))

            // Title field
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth(0.98f)
                    .semantics { contentDescription = "Edit Task Title" },
                singleLine = true
            )

            // Description field
            OutlinedTextField(
                value = desc,
                onValueChange = { desc = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth(0.98f)
                    .heightIn(min = 150.dp)
                    .semantics { contentDescription = "Edit Task Description" },
                maxLines = Int.MAX_VALUE
            )

            // Due Date field
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
                    .semantics { contentDescription = "Edit Task Due Date" },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            if (dateError && date.isNotBlank()) {
                Text(
                    text = "Please use the format dd/mm/yyyy",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 2.dp, start = 8.dp)
                )
            }

            // Dynamic due-date display
            val dueColor = if (isOverdue(date)) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            if (date.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = if (isOverdue(date)) "⚠️ Overdue: $date" else "Due: $date",
                    color = dueColor,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(Modifier.height(20.dp))

            // Completion checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(0.98f)
                    .padding(top = 8.dp)
            ) {
                Checkbox(
                    checked = completed,
                    onCheckedChange = { completed = it }
                )
                Text(
                    text = "Mark as Completed",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(Modifier.height(24.dp))

            // Update Button
            Button(
                onClick = {
                    val updatedTask = task.copy(
                        title = title.trim(),
                        description = desc.trim(),
                        dueDate = date.trim(),
                        isCompleted = completed
                    )
                    viewModel.updateTask(updatedTask)
                    onSave()
                },
                enabled = title.isNotBlank() && date.matches(Regex("""\d{2}/\d{2}/\d{4}""")),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.Start)
                    .semantics { contentDescription = "Update Task Button" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Update Task")
            }

            Spacer(Modifier.height(16.dp))

            // Delete Button
            Button(
                onClick = {
                    viewModel.deleteTask(task)
                    onDelete()
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.Start)
                    .semantics { contentDescription = "Delete Task Button" },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text("Delete Task")
            }
            // Delete Confirmation Dialog
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.deleteTask(task)
                            showDeleteDialog = false
                            onDelete()
                        }) {
                            Text("Yes", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancel")
                        }
                    },
                    title = { Text("Delete Task") },
                    text = { Text("Are you sure you want to delete this task?") }
                )
            }
        }
    }
}