package com.allegro.workshop.builder

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("${Thread.currentThread()} | Start")

        longRunningTask()
    }
    println("${Thread.currentThread()} | End")
}

private suspend fun longRunningTask(){
    println("${Thread.currentThread()} | executing longRunningTask")
    delay(1000)
    println("${Thread.currentThread()} | longRunningTask ends")
}