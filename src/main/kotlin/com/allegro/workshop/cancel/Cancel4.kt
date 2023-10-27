package com.allegro.workshop.cancel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("${Thread.currentThread()} | Start")

    val job: Job = launch(Dispatchers.Default) {
        longRunningFunction()
    }

    delay(1500)
    println("${Thread.currentThread()} | Cancel")
    job.cancelAndJoin()

    println("${Thread.currentThread()} | End")
}

private suspend fun longRunningFunction() {
    repeat(1000) { i ->
        println("${Thread.currentThread().name} | executing :$i step")
        Thread.sleep(600)
    }
}