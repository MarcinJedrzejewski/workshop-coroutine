package com.allegro.workshop.job

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

// convention: suspending functions do not block the caller thread
private suspend fun work(i: Int) = withContext(Dispatchers.IO) {
    Thread.sleep(1000)
    println("${Thread.currentThread()} | Work $i done")
}

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                launch {
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms")
}