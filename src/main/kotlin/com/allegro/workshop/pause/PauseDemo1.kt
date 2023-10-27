package com.allegro.workshop.pause

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.ContinuationInterceptor

suspend fun test1() = coroutineScope  {
    // TODO create pausing dispatcher
    val dispatcher = createPausingDispatcher(this)
    launch(dispatcher) {
        var count = 0
        repeat(20) {
            delay(1000)
            println("i = $count")
            count++
        }
    }

    delay(3000)
    dispatcher.queue.pause()

    delay(3000)
    dispatcher.queue.resume()
}

private fun createPausingDispatcher(scope: CoroutineScope): PausingDispatcher {
    val currentDispatcher = scope.coroutineContext[ContinuationInterceptor] ?: Dispatchers.Default

    return PausingDispatcher(
        queue = PausingDispatchQueue(),
        baseDispatcher = if (currentDispatcher is PausingDispatcher) currentDispatcher.baseDispatcher
        else currentDispatcher as CoroutineDispatcher
    )
}

suspend fun main() {
    test1()
}
