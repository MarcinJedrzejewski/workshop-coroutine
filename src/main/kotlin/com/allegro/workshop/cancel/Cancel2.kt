package com.allegro.workshop.cancel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val parentScope = CoroutineScope(Job() + Dispatchers.IO)

    val job1 = parentScope.launch {
        delay(50)
    }

    val job2 = parentScope.launch {
    }

    val deferred = parentScope.async {
        delay(150)
    }

    delay(100)

    // TODO how cancel only children ?
    parentScope.coroutineContext[Job]?.children?.forEach { it.cancel() }

    // TODO start new coroutine: launch, async
    val job3 = parentScope.launch {
        println("launch")
    }

    job3.join()

    println("Job1: isCancelled = ${job1.isCancelled} ; isCompleted = ${job1.isCompleted}")
    println("Job2: isCancelled = ${job2.isCancelled} ; isCompleted = ${job2.isCompleted}")
    println("Deffered: isCancelled = ${deferred.isCancelled} ; isCompleted = ${deferred.isCompleted}")
}