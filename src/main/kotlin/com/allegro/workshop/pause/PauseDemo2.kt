package com.allegro.workshop.pause

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun test2() = coroutineScope  {
    // TODO create extension launchPausing
    val job = launch {
        var count = 0
        repeat(10) {
            delay(1000)
            println("i = $count")
            count++
        }
    }

    delay(3000)
}

suspend fun main() {
    test2()
}
