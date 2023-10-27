package com.allegro.workshop.builder

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("${Thread.currentThread()} | Start")

        launch {
            longRunningTask()  // calling the long running function
        }

        longRunningTask()
        println("${Thread.currentThread()} | End launch")
    }
    println("${Thread.currentThread()} | End")
}

private suspend fun longRunningTask() {
    println("${Thread.currentThread()} | executing longRunningTask")
    delay(1000)  // simulating the slow behavior by adding a delay
    println("${Thread.currentThread()} | longRunningTask ends")
}