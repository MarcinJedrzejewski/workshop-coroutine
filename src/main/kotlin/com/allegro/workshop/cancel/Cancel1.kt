package com.allegro.workshop.cancel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val parentScope = CoroutineScope(Job() + Dispatchers.IO)

    val job1 = parentScope.launch {
        delay(150)
    }

    val job2 = parentScope.launch {
        delay(200)
    }

    val deferred = parentScope.async {
        delay(150)
    }

    delay(100)

    parentScope.cancel()

    // TODO start new coroutine: launch, async

    println("Job1: isCancelled = ${job1.isCancelled} ; isCompleted = ${job1.isCompleted}")
    println("Job2: isCancelled = ${job2.isCancelled} ; isCompleted = ${job2.isCompleted}")
    println("Deffered: isCancelled = ${deferred.isCancelled} ; isCompleted = ${deferred.isCompleted}")
}