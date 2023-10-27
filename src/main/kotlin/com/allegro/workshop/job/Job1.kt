package com.allegro.workshop.job

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val scope = CoroutineScope(Dispatchers.IO)

fun main() {
    runBlocking {
        val job = scope.launch {
            val job1 = launch {
                delay(2000)
            }
            val job2 = launch {
                delay(3000)
            }
            val job3 = async {
                delay(4000)
            }
        }

        // TODO check structure
    }
}