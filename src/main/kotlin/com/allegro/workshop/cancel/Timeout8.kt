package com.allegro.workshop.cancel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

var counter = 0

class Resource {
    init { counter++ }
    fun close() { counter-- }
}

fun main() {
    runBlocking {
        repeat(10000) {
            launch {
                //TODO leak resources
                val resource = withTimeout(60) {
                    delay(50)
                    Resource()
                }
                resource.close() // Release the resource
            }
        }
    }

    println(counter)
}