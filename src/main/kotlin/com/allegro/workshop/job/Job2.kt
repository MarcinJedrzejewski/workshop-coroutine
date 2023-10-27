package com.allegro.workshop.job

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope {

    val job = Job()
    println(job)

    job.complete()
    println(job)

    // launch is initially active by default
    val activeJob = launch {
        delay(1000)
    }
    println(activeJob)

    activeJob.join()
    println(activeJob)

    // launch started lazily is in New state
    val lazyJob = launch(start = CoroutineStart.LAZY) {
        delay(1000)
    }
    println(lazyJob)

    lazyJob.start()
    println(lazyJob)

    lazyJob.join()
    println(lazyJob)
}