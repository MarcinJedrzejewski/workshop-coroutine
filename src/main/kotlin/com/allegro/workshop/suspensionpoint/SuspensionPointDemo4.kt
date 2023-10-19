package com.allegro.workshop.suspensionpoint

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.util.concurrent.Executors

private val workerPool = Executors.newScheduledThreadPool(1)

suspend fun main() {

    withContext(workerPool.asCoroutineDispatcher()) {

        // TODO change code to force coroutines to cooperate
        launch {
            repeat(10) {
                println("${Thread.currentThread()} | L1")
                Thread.sleep(1000)
                yield()
            }
        }

        launch {
            repeat(10) {
                println("${Thread.currentThread()} | L2")
                Thread.sleep(1000)
                yield()

            }
        }
    }

   workerPool.shutdown()
}