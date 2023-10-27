package com.allegro.workshop.cancel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

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
                var resource: Resource? = null
                try {
                    withTimeout(60) {
                        delay(50)
                        resource = Resource()
                    }
                } finally {
                    resource?.close()
                }
            }
        }
    }

    println(counter)
}