package com.wardatulkeskin_COMP304Lab03_Ex1.data.remote

/**
 * Generic wrapper for representing the state of a network call.
 *
 * Used by the Repository layer to communicate:
 * - Loading state
 * - Successful result with data
 * - Error state with an optional exception
 */
sealed class NetworkResult<out T> {
    /**
     * Network call completed successfully with [data].
     */
    data class Success<T>(val data: T) : NetworkResult<T>()

    /**
     * Network call failed.
     *
     * @param message  Human-readable error message for UI
     * @param throwable Optional underlying exception for logging/debugging
     */
    data class Error(val message: String, val throwable: Throwable? = null) : NetworkResult<Nothing>()

    /**
     * Network call is in progress.
     * The UI can show a loading indicator when this state is emitted.
     */
    object Loading : NetworkResult<Nothing>()
}