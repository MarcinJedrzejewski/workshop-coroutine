package com.allegro.workshop.exception

import kotlinx.coroutines.*

fun main() = runBlocking {

    val handler = CoroutineExceptionHandler { _, exception ->
        //println("CoroutineExceptionHandler got $exception")

        println("CoroutineExceptionHandler got $exception, supressed ${exception.suppressed.contentToString()}}")
    }

    val job = GlobalScope.launch(handler) {
        launch { // the first child
            try {
                delay(2000)
            } finally {
                // TODO throw exception, which wins?
                withContext(NonCancellable) {
                    println("Children are cancelled, but exception is not handled until all children terminate")
                    //delay(5000)
                    throw IllegalStateException()
                    println("The first child finished its non cancellable block")
                }
            }
        }

        launch { // the second child
            delay(10)
            println("Second child throws an exception")
            throw ArithmeticException()
        }
    }
    job.join()
}