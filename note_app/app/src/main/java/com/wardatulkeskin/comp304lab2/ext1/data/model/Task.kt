package com.wardatulkeskin.comp304lab2.ext1.data.model

/**
 * Task.kt
 *
 * Data model class that represents a Task entity in the Task Manager application.
 * Each Task object contains the essential information needed to display,
 * create, update, and track a user's task.
 *
 * This class is part of the "Model" layer in the MVVM architecture.
 *
 * Author: Wardatul Keskin
 * Course: COMP304 – Assignment 2
 */
data class Task(
    /**
     * Unique identifier for each task.
     * Used to differentiate tasks when updating or deleting.
     */
    val id: Int,

    /**
     * Title of the task.
     * Shown prominently in the task list and edit screens.
     */
    val title: String,

    /**
     * Detailed description of the task.
     * Provides more context about what needs to be done.
     */
    val description: String,

    /**
     * Due date for the task (format: dd/MM/yyyy).
     * Used to determine whether a task is overdue or upcoming.
     */
    val dueDate: String,

    /**
     * Status flag indicating whether the task has been completed.
     * Default value is false (Pending).
     */
    var isCompleted: Boolean = false
)