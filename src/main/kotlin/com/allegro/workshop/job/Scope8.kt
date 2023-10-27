package com.allegro.workshop.job

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private suspend fun longTask() = coroutineScope {
    launch {
        delay(1000)
        throw IllegalStateException()
    }
    launch {
        delay(2000)
        println("Done2")
    }
    launch {
        delay(3000)
        println("Done3")
    }

}

suspend fun main() {
    println("Before")
    longTask()
    println("After")
}