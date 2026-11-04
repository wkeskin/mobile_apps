package com.wardatulkeskin_COMP304Lab03_Ex1.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wardatulkeskin_COMP304Lab03_Ex1.data.local.WeatherDatabase
import com.wardatulkeskin_COMP304Lab03_Ex1.data.remote.WeatherApi
import com.wardatulkeskin_COMP304Lab03_Ex1.data.repository.WeatherRepository
import com.wardatulkeskin_COMP304Lab03_Ex1.data.repository.WeatherRepositoryImpl
import com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel.WeatherDetailsViewModel
import com.wardatulkeskin_COMP304Lab03_Ex1.viewmodel.WeatherListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://api.open-meteo.com/"

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

/**
 * Koin dependency injection module for the Weather App.
 *
 * Responsibilities:
 * - Provide Retrofit instance for API calls
 * - Provide Room database + DAO
 * - Provide repository implementation
 * - Provide ViewModels
 *
 * This ensures clean MVVM architecture:
 * View → ViewModel → Repository → API/Room
 */

val appModule = module {

    // Dispatcher for IO-bound tasks (database & network)
    single { Dispatchers.IO }

    // ---------- ROOM DATABASE ----------
    /**
     * Provides a singleton Room database.
     * Stored as "weather_db" in device storage.
     */
    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "weather_db"
        ).build()
    }

    // DAO provider
    single { get<WeatherDatabase>().weatherDao() }

    // ---------- RETROFIT CLIENT ----------
    /**
     * Retrofit builder with Gson converter.
     * BASE_URL: https://api.open-meteo.com/
     *
     * Gson automatically converts JSON responses into
     * our Kotlin data classes (OpenMeteoResponse, etc.).
     */
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    // Retrofit API implementation
    single<WeatherApi> {
        get<Retrofit>().create(WeatherApi::class.java)
    }

    // ---------- REPOSITORY ----------
    /**
     * Repository abstracts data source (API + Room).
     * ViewModels depend on this abstraction (not concrete impl).
     */
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            api = get(),
            dao = get()
        )
    }

    // ---------- VIEWMODELS ----------
    /**
     * ViewModel for list screen.
     */
    viewModel {
        WeatherListViewModel(
            repository = get(),
            ioDispatcher = get()
        )
    }

    /**
     * ViewModel for detail screen.
     */
    viewModel {
        WeatherDetailsViewModel(
            repository = get()
        )
    }
}
