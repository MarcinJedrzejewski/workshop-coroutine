package com.allegro.workshop.exception

import kotlinx.coroutines.*
import java.io.*

val handler = CoroutineExceptionHandler { _, exception ->
    println("CoroutineExceptionHandler got $exception")
}

fun main() = runBlocking {

    val scope = CoroutineScope(Job())

    val job = scope.launch(handler) {
        val inner1 = launch { // all this stack of coroutines will get cancelled
            launch {
                // TODO how dont propagate exception
                supervisorScope {
                    launch {
                        throw IOException()
                    } // the original exception
                }
                delay(1000)
                println("end")
           }
        }

        try {
            inner1.join()
        } catch (e: CancellationException) {
            println("Rethrowing CancellationException with original cause: ${e.cause}")
            throw e // cancellation exception is rethrown, yet the original IOException gets to the handler
        }
    }

    job.join()
}