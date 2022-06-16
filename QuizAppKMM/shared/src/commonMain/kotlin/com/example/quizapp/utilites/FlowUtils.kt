package com.example.quizapp.utilites

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
fun <T : Any> Flow<T>.wrap(): CFlow<T> = CFlow(this)

class CFlow<T : Any>(private val origin: Flow<T>) : Flow<T> by origin {
    fun onChange(provideNewState: ((T) -> Unit)): Closeable {
        val job = Job()

        origin.onEach {
            provideNewState(it)
        }.launchIn(
            CoroutineScope(Dispatchers.Main + job)
        )

        return Closeable { job.cancel() }
    }
}

fun interface Closeable {
    fun close()
}