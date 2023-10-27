package com.allegro.workshop.job

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

private fun work(i: Int) {
    Thread.sleep(1000)
    println("Work $i done")
}

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                launch(Dispatchers.Default) { // TODO set Job
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms")
}