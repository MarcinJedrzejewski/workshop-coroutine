package com.allegro.workshop.dispatch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun test() {
    println("${Thread.currentThread()} | 1")
    delay(1000)
    // return to default thread
    println("${Thread.currentThread()} | 2")
}

suspend fun main() {
    withContext(Dispatchers.Default) {
        test()
    }
}