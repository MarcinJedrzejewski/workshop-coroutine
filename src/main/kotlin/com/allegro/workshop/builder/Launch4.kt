package com.allegro.workshop.builder

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

private suspend fun doWork() {
    delay(1000)
    println("${Thread.currentThread()} | Work")
    delay(1000)
}

fun main() {

    val time = measureTimeMillis {
        runBlocking {
            launch {
                doWork()
            }
            // TODO implement myLaunch
        }
    }

    println("End $time")
}