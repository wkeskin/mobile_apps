package com.wardatulkeskin.comp304lab2.ext1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wardatulkeskin.comp304lab2.ext1.data.model.Task
import com.wardatulkeskin.comp304lab2.ext1.data.repository.TaskRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * TaskViewModel.kt
 *
 * The ViewModel responsible for managing UI-related data for the Task Manager app.
 * It connects the UI (View layer) with the data (Model layer) through the TaskRepository.
 *
 * Responsibilities:
 * - Exposes a reactive StateFlow list of tasks to the UI.
 * - Handles user interactions such as adding, updating, and deleting tasks.
 * - Maintains app state across configuration changes (e.g., screen rotations).
 * - Ensures all data operations are performed asynchronously using coroutines.
 *
 * This class represents the "ViewModel" layer in the MVVM architecture.
 *
 * Author: Wardatul Keskin
 * Course: COMP304 – Assignment 2
 */
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val tasks: StateFlow<List<Task>> = repository.tasks

    fun addTask(task: Task) {
        viewModelScope.launch { repository.addTask(task) }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch { repository.updateTask(task) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { repository.deleteTask(task) }
    }

    fun getTaskById(id: Int): Task? = repository.getTaskById(id)
}