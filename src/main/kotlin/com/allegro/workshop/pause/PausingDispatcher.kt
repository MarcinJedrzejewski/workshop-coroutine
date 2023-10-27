package com.allegro.workshop.pause

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

// TODO implement dispatcher with pausing feature
class PausingDispatcher(
    val queue: PausingDispatchQueue,
    val baseDispatcher: CoroutineDispatcher
) : CoroutineDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (isPaused(context)) {
            queue.queue(context, block, baseDispatcher)
        } else {
            baseDispatcher.dispatch(context, block)
        }
    }

    @InternalCoroutinesApi
    override fun dispatchYield(context: CoroutineContext, block: Runnable) {
        if (isPaused(context)) {
            queue.queue(context, block, baseDispatcher)
        } else {
            baseDispatcher.dispatchYield(context, block)
        }
    }

    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        return super.isDispatchNeeded(context) || isPaused(context)
    }

    private fun isPaused(context: CoroutineContext): Boolean {
        return queue.isPaused && context[NonPausing.key] == null
    }

    override fun toString(): String {
        return "PausingDispatcher"
    }
}