package com.allegro.workshop.job

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.system.measureTimeMillis

private suspend fun work(i: Int) {
    //Thread.sleep(1000)
    delay(1000)
    println("Work $i done")
}

// TODO create new coroutine scope

private object MyScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext
}

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                MyScope.launch(coroutineContext) {
                    work(i)
                }
            }
        }
    }

    ///Thread.sleep(3000)
    println("Done in $time ms")
}