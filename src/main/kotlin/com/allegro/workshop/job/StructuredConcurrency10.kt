package com.allegro.workshop.job

import kotlinx.coroutines.*
import kotlin.system.*

fun main() {
    runBlocking {
        val time = measureTimeMillis {
            val one = somethingUsefulOneAsync()
            val two = somethingUsefulTwoAsync(this)
            //throw IllegalArgumentException()
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }
    Thread.sleep(3000)
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L)
    println("return 13")
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L)
    println("return 29")
    return 29
}

suspend fun somethingUsefulOneAsync() = coroutineScope {
    async {
        doSomethingUsefulOne()
    }
}

suspend fun somethingUsefulTwoAsync(scope: CoroutineScope) =
    scope.async {
        doSomethingUsefulTwo()
    }