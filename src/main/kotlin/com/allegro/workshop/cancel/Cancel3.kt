package com.allegro.workshop.cancel

import kotlinx.coroutines.*

fun main() = runBlocking {

    val parentScope = CoroutineScope(Job() + Dispatchers.IO)

    val job1 = parentScope.launch {
        delay(150L)
    }

    val job2 = parentScope.launch {
    }

    val deferred = parentScope.async {
        delay(150L)
    }

    deferred.cancel()
    // can deferred.await() ?

    println("Job1: isCancelled = ${job1.isCancelled} ; isCompleted = ${job1.isCompleted}")
    println("Job2: isCancelled = ${job2.isCancelled} ; isCompleted = ${job2.isCompleted}")
    println("Deffered: isCancelled = ${deferred.isCancelled} ; isCompleted = ${deferred.isCompleted}")
}