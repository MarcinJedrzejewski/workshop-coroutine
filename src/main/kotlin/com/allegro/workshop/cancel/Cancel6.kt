package com.allegro.workshop.cancel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    runBlocking {
        val job = launch(Dispatchers.Default) {
            repeat(5) { i ->
                // TODO add finally
                try {
                    println("sleep $i ...")
                    delay(500)
                } finally {
                    withContext(NonCancellable) {
                        println("finally")
                        releaseResource()
                        println("end finally")
                    }
                }
            }
        }
        delay(1300L) // delay a bit

        println("Cancel")
        job.cancelAndJoin()

        println("End")
    }
}

suspend fun releaseResource() {
    delay(1000)
}