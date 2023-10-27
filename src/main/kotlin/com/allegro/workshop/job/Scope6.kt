package com.allegro.workshop.job

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private suspend fun longTask() {
    val scope = CoroutineScope(SupervisorJob())
    val job1 = scope.launch {
        delay(1000)
        throw Error()
    }
    val job2 = scope.launch {
        delay(2000)
        println("Done2")
    }
    val job3 = scope.launch {
        delay(3000)
        println("Done3")
    }

    job1.join()
    job2.join()
    job3.join()
}

suspend fun main() {
    println("Before")
    longTask()
    println("After")
}