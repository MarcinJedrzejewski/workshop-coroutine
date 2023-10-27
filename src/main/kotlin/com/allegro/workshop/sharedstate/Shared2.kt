package com.allegro.workshop.sharedstate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


//TOOD implement counter actor

fun main() {
    runBlocking {
        // create the actor

        withContext(Dispatchers.Default) {
            massiveRun {
                // increment counter
            }
        }

        // send a message to get a counter value from an actor

        // shutdown the actor
    }
}