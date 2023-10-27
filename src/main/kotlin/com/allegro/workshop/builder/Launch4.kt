package com.allegro.workshop.builder

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.system.measureTimeMillis
import kotlin.coroutines.intrinsics.createCoroutineUnintercepted

private suspend fun doWork() {
    delay(1000)
    println("${Thread.currentThread()} | Work")
    delay(1000)
}

fun myLaunch(block: suspend () -> Unit) {
    val callback = object : Continuation<Unit> {
        override val context: CoroutineContext = EmptyCoroutineContext
        override fun resumeWith(result: Result<Unit>) {}
    }

    block.createCoroutineUnintercepted(callback).resumeWith(Result.success(Unit))
}

fun main() {

    val time = measureTimeMillis {
        runBlocking {
            // TODO implement myLaunch
            myLaunch {
                doWork()
            }
        }
    }

    Thread.sleep(3000)
    println("End $time")
}