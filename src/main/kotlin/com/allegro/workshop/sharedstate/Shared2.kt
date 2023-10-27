package com.allegro.workshop.sharedstate

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.channels.actor


sealed class CounterMsg
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()
data object IncCounter : CounterMsg()

//TOOD implement counter actor

fun CoroutineScope.creteActor() = actor<CounterMsg> {
    var counter = 0
    for (msg in channel) {
        when(msg) {
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}

fun main() {
    runBlocking {
        // create the actor
        val actor = creteActor()

        withContext(Dispatchers.Default) {
            massiveRun {
                // increment counter
                actor.send(IncCounter)
            }
        }

        // send a message to get a counter value from an actor
        val response = CompletableDeferred<Int>()
        actor.send(GetCounter(response))

        println("Counter = ${response.await()}")

        // shutdown the actor
        actor.close()
    }
}