package com.allegro.workshop.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.function.Predicate

fun main() {
    runBlocking {
        streamingNumbers()
    }
}

suspend fun streamingNumbers() = coroutineScope {
    launch {
        val numbers = produceNumbers(4)

        // Filtering out even numbers
        val filtered = filter(numbers) { it % 2 == 0}

        // Squaring the remaining odd numbers
        val sum = map(filtered) { it + it}

        // Summing them up
        val res = reduce(sum) { acc, x -> acc + x }

        res.consumeEach {
            println(it)
        }
    }
}

// Producing numbers, each number being sent to the pipeline
fun CoroutineScope.produceNumbers(count: Int): ReceiveChannel<Int> = produce {
    for (i in 1..count) send(i)
}

// TODO implement operators in pipeline
fun CoroutineScope.filter(
    channel: ReceiveChannel<Int>,
    predicate: (Int) -> Boolean
): ReceiveChannel<Int> = produce {
    channel.consumeEach {
        if (predicate(it)) send(it)
    }
}

fun CoroutineScope.map(
    channel: ReceiveChannel<Int>,
    mapFunction: (Int) -> Int
) = produce {
    channel.consumeEach {
        send(mapFunction(it))
    }
}

fun CoroutineScope.reduce(
    channel: ReceiveChannel<Int>, accFunction: (Int, Int) -> Int
) = produce {
    var res = 0
    channel.consumeEach {
        res = accFunction(res , it)
    }
    send(res)
}