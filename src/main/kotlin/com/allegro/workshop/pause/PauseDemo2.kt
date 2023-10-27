package com.allegro.workshop.pause

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

suspend fun test2() = coroutineScope  {
    // TODO create extension launchPausing
    val job = launchPausing {
        var count = 0
        repeat(10) {
            delay(1000)
            println("i = $count")
            count++
        }
    }

    delay(3000)
    job.pause()

    delay(3000)
    job.resume()
}

fun CoroutineScope.launchPausing(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) : PausingJob {
    val dispatcher = createPausingDispatcher(this, context)
    val job = launch(context + dispatcher, start, block)
    return PausingJob(job, dispatcher.queue)
}

class PausingJob(
    private val job: Job,
    private val pausingHandle: PausingHandle
): Job by job, PausingHandle by pausingHandle

private fun createPausingDispatcher(scope: CoroutineScope, newContext: CoroutineContext): PausingDispatcher {
    val currentDispatcher = newContext[ContinuationInterceptor]
        ?: scope.coroutineContext[ContinuationInterceptor]
        ?: Dispatchers.Default

    return PausingDispatcher(
        queue = PausingDispatchQueue(),
        baseDispatcher = if (currentDispatcher is PausingDispatcher) currentDispatcher.baseDispatcher
        else currentDispatcher as CoroutineDispatcher
    )
}

suspend fun main() {
    test2()
}
