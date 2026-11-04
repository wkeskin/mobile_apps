package com.wardatulkeskin_COMP304Lab03_Ex1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.wardatulkeskin_COMP304Lab03_Ex1.navigation.AppNavigationContent
import com.wardatulkeskin_COMP304Lab03_Ex1.ui.theme.MyApplicationTheme

/**
* Single-activity entry point for the Weather app.
*
* This activity hosts all UI using Jetpack Compose and Navigation.
* All screens are rendered inside [AppNavigationContent].
*/
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                AppNavigationContent(
                    // keep default params
                )
            }
        }
    }
}
