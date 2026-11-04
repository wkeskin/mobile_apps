package com.wardatulkeskin.comp304lab2.ext1.di

import com.wardatulkeskin.comp304lab2.ext1.data.repository.TaskRepository
import com.wardatulkeskin.comp304lab2.ext1.data.repository.TaskRepositoryImpl
import com.wardatulkeskin.comp304lab2.ext1.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * AppModule.kt
 *
 * This file defines the Koin dependency injection (DI) module for the Task Manager app.
 * It registers singleton instances and ViewModels used throughout the application.
 *
 * The DI configuration ensures loose coupling between classes by automatically providing
 * dependencies where needed. This promotes cleaner architecture and easier testing.
 *
 * Key Bindings:
 * - Provides a single instance of [TaskRepositoryImpl] whenever [TaskRepository] is required.
 * - Provides a [TaskViewModel] instance that receives the repository via constructor injection.
 *
 * This module is loaded in the Application class (via Koin) to make the dependencies
 * available across the app lifecycle.
 *
 * Author: Wardatul Keskin
 * Course: COMP304 – Assignment 2
 */
val appModule = module {
    /**
     * Provides a singleton instance of [TaskRepository].
     * The [TaskRepositoryImpl] implementation is used for handling
     * all task-related CRUD operations and data flow.
     */
    single<TaskRepository> { TaskRepositoryImpl() }

    /**
     * Provides a [TaskViewModel] instance using Koin's ViewModel DSL.
     * The repository instance is automatically injected into the ViewModel.
     */
    viewModel { TaskViewModel(get()) }
}