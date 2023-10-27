package com.allegro.workshop.pause

import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext


// TODO implement PausingDispatchQueue
class PausingDispatchQueue : PausingHandle {

    private val paused = AtomicBoolean(false)
    private val queue = ArrayDeque<Resumer>()

    override val isPaused: Boolean
        get() = paused.get()

    override fun pause() {
        paused.set(true)
    }

    override fun resume() {
        if (paused.compareAndSet(true, false)) {
            dispatchNext()
        }
    }
    private fun dispatchNext() {
        val resumer = queue.removeFirstOrNull() ?: return
        resumer.dispatch()
    }

    fun queue(context: CoroutineContext, block: Runnable, dispatcher: CoroutineDispatcher) {
        queue.addLast(Resumer(dispatcher, context, block))
    }

    override fun toString(): String {
        return "PausingDispatchQueue@${hashCode()}"
    }

    private inner class Resumer(
        private val dispatcher: CoroutineDispatcher,
        private val context: CoroutineContext,
        private val block: Runnable
    ) : Runnable {

        override fun run() {
            block.run()
            if (!paused.get()) {
                dispatchNext()
            }
        }

        fun dispatch() {
            dispatcher.dispatch(context, this)
        }
    }
}