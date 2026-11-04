package com.wardatulkeskin_COMP304Lab03_Ex1

import android.app.Application
import com.wardatulkeskin_COMP304Lab03_Ex1.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Custom Application class for the Weather app.
 *
 * It starts Koin so that koinViewModel(), repositories,
 * Retrofit, and Room are all available when the app launches.
 */
class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin events to Logcat (optional but useful while developing)
            androidLogger()

            // Provide Android context to Koin
            androidContext(this@WeatherApp)

            // Load our DI module (defined in Modules.kt)
            modules(appModule)
        }
    }
}
