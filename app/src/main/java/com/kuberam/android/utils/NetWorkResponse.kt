package com.kuberam.android.utils

sealed class NetworkResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : NetworkResponse<T>(data = data)
    class Failure<T>(message: String?) : NetworkResponse<T>(message = message)
    class Loading<T>() : NetworkResponse<T>()
}
