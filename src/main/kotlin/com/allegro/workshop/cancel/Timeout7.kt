package com.allegro.workshop.cancel

import kotlinx.coroutines.*

fun main() = runBlocking {
    withTimeout(1300L) {
        try {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        } catch (e: Exception) {
            println(e)
        }
    }
}