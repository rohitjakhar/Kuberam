package com.kuberam.android.utils

suspend fun <T : Any> safeFirebaseCall(
    successMessage: String = "",
    errorMessage: String = "",
    nullDataMessage: String = "",
    handleNullChecking: Boolean = false,
    call: suspend () -> T?
): NetworkResponse<T> = try {
    val response = call()
    if (handleNullChecking) handleNullCheck(response, successMessage, nullDataMessage)
    else NetworkResponse.Success(data = null, successMessage)
} catch (e: Exception) {
    NetworkResponse.Failure(message = if (errorMessage.isNotEmpty()) errorMessage else e.message.toString())
}

private fun <T> handleNullCheck(
    response: T?,
    successMessage: String,
    nullDataMessage: String
): NetworkResponse<T> {
    return response?.let { NetworkResponse.Success(response, successMessage) }
        ?: NetworkResponse.Failure(message = nullDataMessage)
}
