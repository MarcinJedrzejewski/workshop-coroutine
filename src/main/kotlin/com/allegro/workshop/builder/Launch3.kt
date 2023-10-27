package com.allegro.workshop.builder

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("${Thread.currentThread()} | Start")

        val job = launch(start = CoroutineStart.LAZY) {
            longRunningTask()
        }

        job.join()
        println("${Thread.currentThread()} | End launch")
    }
    println("${Thread.currentThread()} | End")
}

private suspend fun longRunningTask() {
    println("${Thread.currentThread()} | executing longRunningTask")
    delay(1000)  // simulating the slow behavior by adding a delay
    println("${Thread.currentThread()} | longRunningTask ends")
}