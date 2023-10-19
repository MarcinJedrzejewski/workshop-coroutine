package com.allegro.workshop.pause

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun test1() = coroutineScope  {
    // TODO create pausing dispatcher
    launch {
        var count = 0
        repeat(20) {
            delay(1000)
            println("i = $count")
            count++
        }
    }
    delay(3000)
}

suspend fun main() {
    test1()
}
