package com.allegro.workshop.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main() {
    runBlocking {
        // create a rendezvous channel with capacity 0
        val channel = Channel<Int>(0) // TODO change buffer

        val startTime = System.currentTimeMillis()

        // launch the producer coroutine
        launch {
            for (i in 1..5) {
                log("Producer -> Sending $i", startTime)
                channel.send(i)
                log("Producer -> Sent $i", startTime)
            }
            channel.close() // close the channel after sending all data
        }

        // launch the consumer coroutine
        launch {
            // iterate over the channel until it's closed
            for (value in channel) {
                log("Consumer Received $value", startTime)
                yield()
            }
        }
    }
}

private fun log(message: String, startTime: Long) {
    val currentTime = System.currentTimeMillis()
    val diffTime = String.format("%.3f", (currentTime - startTime).toDouble() / 1000)
    println("${Thread.currentThread()} | [$diffTime] $message")
}