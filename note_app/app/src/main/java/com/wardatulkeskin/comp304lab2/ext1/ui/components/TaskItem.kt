package com.wardatulkeskin.comp304lab2.ext1.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wardatulkeskin.comp304lab2.ext1.data.model.Task

/**
 * TaskItem.kt
 *
 * A reusable composable component that displays a single task item inside a Material Design 3 Card.
 * Used within a LazyColumn on the Home screen to show task details such as:
 * - Title
 * - Due date
 * - Description (limited to two lines)
 * - Completion status (Pending / Completed)
 *
 * The entire card is clickable and triggers navigation to the View/Edit Task screen.
 *
 * Part of the "View" layer in the MVVM architecture.
 *
 * Author: Wardatul Keskin
 * Course: COMP304 – Assignment 2
 */
@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    // Card Container setting
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // title
            Text(task.title, style = MaterialTheme.typography.titleMedium)
            // due dates
            Text("Due: ${task.dueDate}", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
            // description
            Text(task.description, style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            // completion checklist
            Text(
                text = if (task.isCompleted) "Completed" else "Pending",
                color = if (task.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
    }
}