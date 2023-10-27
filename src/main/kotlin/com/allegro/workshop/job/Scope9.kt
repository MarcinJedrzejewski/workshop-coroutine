package com.allegro.workshop.job

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    // Job is not inherited !
    runBlocking(SupervisorJob()) {
        launch {
            delay(1000)
            throw Error()
        }
        launch {
            delay(2000)
            println("Done")
        }
        launch {
            delay(3000)
            println("Done")
        }
    }
}