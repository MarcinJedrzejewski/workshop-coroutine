package com.allegro.workshop.cancel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.coroutines.coroutineContext

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

        delay(1000)
        //Thread.sleep(600)
        //yield()
    }
}