package com.allegro.workshop.job

import kotlinx.coroutines.*
import kotlin.system.*

fun main() {
    val time = measureTimeMillis {
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()

        // TODO throw exception
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
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