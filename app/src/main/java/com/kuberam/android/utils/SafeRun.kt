package com.kuberam.android.utils

import kotlinx.coroutines.CancellationException

inline fun <T, R> T.safeResult(block: T.() -> R): Result<R> = try {
    Result.success(block())
} catch (e: Throwable) {
    if (e is CancellationException) throw e
    Result.failure(e)
}

inline fun <T, R> T.safeResultUnit(block: T.() -> R): Result<Unit> = safeResult(block).map { }
