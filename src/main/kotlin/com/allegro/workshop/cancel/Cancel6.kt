package com.allegro.workshop.cancel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val job = launch(Dispatchers.Default) {
            repeat(5) { i ->
                // TODO add finally
                println("sleep $i ...")
                delay(500)

            }
        }
        delay(1300L) // delay a bit

        println("Cancel")
        job.cancelAndJoin()

        println("End")
    }
}