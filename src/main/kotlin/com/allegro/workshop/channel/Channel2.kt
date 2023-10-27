package com.allegro.workshop.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val channel = Channel<Int>()

        val startTime = System.currentTimeMillis()

        // launch the producer coroutine
        launch {
            for (i in 1..5) {
                log("Producer1 -> Sending $i", startTime)
                channel.send(i)

                val value = channel.receive()
                log("Consumer1 Received $value", startTime)

            }
            channel.close() // close the channel after sending all data
        }

        // launch the consumer coroutine
        launch {
            // iterate over the channel until it's closed
            while (!channel.isClosedForReceive) {
                val value = channel.receive()
                log("Consumer2 Received $value", startTime)

                val res = value * value
                log("Producer2 -> Sending $res", startTime)
                channel.send(res)
            }
        }
    }
}

private fun log(message: String, startTime: Long) {
    val currentTime = System.currentTimeMillis()
    val diffTime = String.format("%.3f", (currentTime - startTime).toDouble() / 1000)
    println("${Thread.currentThread()} | [$diffTime] $message")
}