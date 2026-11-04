package com.wardatulkeskin.comp304lab2.ext1.data.repository

import com.wardatulkeskin.comp304lab2.ext1.data.model.Task
import kotlinx.coroutines.flow.StateFlow

/**
 * TaskRepository.kt
 *
 * Interface defining the contract for managing Task data in the Task Manager app.
 * It abstracts the data operations layer from the rest of the app,
 * allowing flexibility in changing data sources (e.g., in-memory, database, or API)
 * without affecting the ViewModel or UI layers.
 *
 * This interface is part of the "Model" layer in the MVVM architecture.
 *
 * Author: Wardatul Keskin
 * Course: COMP304 – Assignment 2
 */
interface TaskRepository {
    /**
     * A read-only StateFlow that emits the current list of all tasks.
     * Observed by the ViewModel and UI to automatically update when data changes.
     */
    val tasks: StateFlow<List<Task>>

    /**
     * Adds a new Task to the task list.
     *
     * @param task The Task object to be added.
     */
    fun addTask(task: Task)

    /**
     * Updates an existing Task in the list.
     * Matches tasks by their unique ID and replaces the corresponding entry.
     *
     * @param task The updated Task object.
     */
    fun updateTask(task: Task)

    /**
     * Deletes a Task from the list.
     *
     * @param task The Task object to remove.
     */
    fun deleteTask(task: Task)

    /**
     * Retrieves a specific Task by its unique ID.
     *
     * @param id The ID of the Task to find.
     * @return The Task object if found, otherwise null.
     */
    fun getTaskById(id: Int): Task?
}