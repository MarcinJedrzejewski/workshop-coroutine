package com.allegro.workshop.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        streamingNumbers()
    }
}

suspend fun streamingNumbers() = coroutineScope {
    launch {
        val numbers = produceNumbers(4)

        // Filtering out even numbers

        // Squaring the remaining odd numbers

        // Summing them up

        //println(sum)
    }
}

// Producing numbers, each number being sent to the pipeline
fun CoroutineScope.produceNumbers(count: Int): ReceiveChannel<Int> = produce {
    for (i in 1..count) send(i)
}

// TODO implement operators in pipeline