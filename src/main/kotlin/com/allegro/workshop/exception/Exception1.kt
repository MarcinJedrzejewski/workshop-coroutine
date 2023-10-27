package com.allegro.workshop.exception

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val scope = CoroutineScope(Job())

        // TODO async
        try {
            val job = scope.async {
                println("${Thread.currentThread()} | launch")
                delay(1000)
                println("${Thread.currentThread()} | throw exception")
                throw SomeException() // some exception is thrown by a lower layer
            }
            job.await()
        } catch (e: SomeException) {
            println("${Thread.currentThread()} | Catch exception: $e")
        }
    }
}

private class SomeException : RuntimeException("Some")