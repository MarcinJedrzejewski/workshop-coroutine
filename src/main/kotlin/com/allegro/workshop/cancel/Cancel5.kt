package com.allegro.workshop.cancel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

fun main() {
    runBlocking {
        val job = launch(Dispatchers.Default) {
            repeat(5) { i ->
                // TODO add catch
                println("sleep $i ...")
                delay(500)
            }
        }

        delay(1300L) // delay a bit

        println("Cancel")
        job.cancelAndJoin() // cancels the job and waits for its completion

        println("End")
    }
}