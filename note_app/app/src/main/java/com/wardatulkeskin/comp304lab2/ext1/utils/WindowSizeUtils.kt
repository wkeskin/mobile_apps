package com.wardatulkeskin.comp304lab2.ext1.utils

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
* WindowSizeUtils.kt
*
* Utility function that determines the current window size class
* (Compact, Medium, or Expanded) for responsive UI design in Jetpack Compose.
*
* This function helps adapt layouts for various screen types such as:
* - Phones (Compact)
* - Tablets (Medium)
* - Foldables / Large Screens (Expanded)
*
* It is used in the HomeScreen to dynamically adjust the number of task columns.
*
* Part of the "Utility" layer in the MVVM architecture.
*
* Author: Wardatul Keskin
* Course: COMP304 – Assignment 2
*/
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    // Get the current context and ensure it’s an Activity instance
    val context = LocalContext.current
    val activity = context as? Activity
        ?: throw IllegalStateException("WindowSizeClass can only be calculated in an Activity context")

    // Calculate the current WindowSizeClass (Compact, Medium, Expanded) using material 3 responsive layout
    return calculateWindowSizeClass(activity)
}