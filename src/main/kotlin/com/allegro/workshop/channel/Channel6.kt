package com.allegro.workshop.channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val channel = Channel<String>()

    launch { sendString(channel, "1", 200L) }
    launch { sendString(channel, "2", 500L) }

    repeat(6) { // receive first six
        println(channel.receive())
    }

    coroutineContext.cancelChildren() // cancel all children to let main finish
}

suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
    while (true) {
        delay(time)
        channel.send(s)
    }
}