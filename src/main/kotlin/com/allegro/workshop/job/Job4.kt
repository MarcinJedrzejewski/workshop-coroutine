package com.allegro.workshop.job

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

private fun work(i: Int) {
    Thread.sleep(1000)
    println("Work $i done")
}

// TODO create new coroutine scope

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                // TODO how to join children?
                launch {
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms")
}