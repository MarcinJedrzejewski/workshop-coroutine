package com.allegro.workshop.job

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.system.measureTimeMillis

private fun work(i: Int) {
    Thread.sleep(1000)
    println("Work $i done")
}


fun main() {
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                GlobalScope.launch(Dispatchers.Default) { // TODO set Job
                    work(i)
                }
            }
        }
    }

    Thread.sleep(3000)
    println("Done in $time ms")
}