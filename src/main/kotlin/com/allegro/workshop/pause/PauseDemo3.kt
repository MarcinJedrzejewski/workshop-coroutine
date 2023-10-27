package com.allegro.workshop.pause

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun test3() = coroutineScope {
    val job = launchPausing {

        // TODO set NonPausing in context
        launch(NonPausing) {
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
    job.pause()

    delay(3000)
    job.resume()
}

suspend fun main() {
    test3()
}
