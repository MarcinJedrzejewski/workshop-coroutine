package com.allegro.workshop.job

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay

private suspend fun longTask() {
    val scope = CoroutineScope(Job())
    val def1 = scope.async {
        delay(1000)
        // TODO throw Error()
    }
    val def2 = scope.async {
        delay(2000)
        println("Done2")
    }
    val def3 = scope.async {
        delay(3000)
        println("Done3")
    }

    listOf(def1, def2, def3).awaitAll()
}

suspend fun main() {
    println("Before")
    longTask()
    println("After")
}