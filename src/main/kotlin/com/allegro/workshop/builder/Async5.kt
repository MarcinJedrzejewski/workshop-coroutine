package com.allegro.workshop.builder

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

fun main() {
    runBlocking {
        println("${Thread.currentThread()} | Start")

        val res = async {
            longRunningTask()  // calling the long running function
        }

        val id = res.await()
        println("${Thread.currentThread()} | End async: $id")
    }
    println("${Thread.currentThread()} | End")
}

private suspend fun longRunningTask(): String {
    println("${Thread.currentThread()} | executing longRunningTask")
    delay(1000)  // simulating the slow behavior by adding a delay
    println("${Thread.currentThread()} | longRunningTask ends")

    return UUID.randomUUID().toString()
}