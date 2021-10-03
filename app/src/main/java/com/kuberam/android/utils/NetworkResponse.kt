package com.kuberam.android.utils

sealed class NetworkResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?, message: String? = null) :
        NetworkResponse<T>(data = data, message = message)

    class Failure<T>(message: String) : NetworkResponse<T>(message = message)
    class Loading<T> : NetworkResponse<T>()
}

fun NetworkResponse<*>.mapToUnit() = when (this) {
    is NetworkResponse.Failure -> NetworkResponse.Failure<Unit>(message = message.toString())
    is NetworkResponse.Loading -> NetworkResponse.Loading()
    is NetworkResponse.Success -> NetworkResponse.Success(data = Unit, message = message.toString())
}
