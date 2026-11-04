package com.wardatulkeskin.comp304lab2.ext1.data.repository

import com.wardatulkeskin.comp304lab2.ext1.data.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
* TaskRepositoryImpl.kt
*
* Implementation of the [TaskRepository] interface.
* This class serves as an in-memory data source for managing a list of [Task] objects.
* It uses Kotlin's [MutableStateFlow] to provide a reactive data stream
* that updates the UI automatically whenever the data changes.
*
* This class represents the "Model" layer in the MVVM architecture
* and provides CRUD (Create, Read, Update, Delete) operations for tasks.
*
* Author: Wardatul Keskin
* Course: COMP304 – Assignment 2
*/
class TaskRepositoryImpl : TaskRepository {
    /**
     * Internal mutable StateFlow that holds the current list of tasks.
     * Exposed as an immutable [StateFlow] to prevent direct modification
     * from outside the repository.
     */
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    /**
     * Public immutable StateFlow observed by the ViewModel.
     * Any changes to the task list automatically trigger UI recomposition.
     */
    override val tasks = _tasks.asStateFlow()

    // Add the new task to the top of the list (recent first)
    override fun addTask(task: Task) {
        _tasks.value = listOf(task) + _tasks.value
    }

    // Replace the old task with the updated version
    override fun updateTask(task: Task) {
        _tasks.value = _tasks.value.map { if (it.id == task.id) task else it }
    }

    // Remove the task from the list
    override fun deleteTask(task: Task) {
        _tasks.value = _tasks.value.filter { it.id != task.id }
    }

    // Find a task by its ID
    override fun getTaskById(id: Int): Task? =
        _tasks.value.find { it.id == id }
}