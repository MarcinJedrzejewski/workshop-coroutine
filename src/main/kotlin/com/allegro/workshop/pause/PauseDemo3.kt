package com.allegro.workshop.pause

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun test3() = coroutineScope {
    val job = launch {

        // TODO set NonPausing in context
        launch {
            var count = 0
            repeat(10) {
                delay(1000)
                println("i = $count")
                count++
            }
        }

        launch {
            var count = 0
            repeat(10) {
                delay(1000)
                println("k = $count")
                count++
            }
        }
    }

    delay(3000)
}

suspend fun main() {
    test3()
}
