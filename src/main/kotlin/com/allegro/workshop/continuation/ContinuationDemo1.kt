package com.allegro.workshop.continuation

import com.allegro.workshop.callback.User
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class ContinuationDemo1 {

    // TODO Implement continuations class per function
    class FetchAndShowUser(private val continuation: Continuation<Any?>): Continuation<Any?> {

        var label: Int = 0
        var user: User? = null
        var res: Any? = null

        override fun resumeWith(result: Result<Any?>) {
            res = result.getOrNull()

            when (label) {
                0 -> {
                    label = 1
                    FetchUser(this).resumeWith(Result.success(Unit))
                }
                1 -> {
                    label = 2
                    user = res as User
                    println(user)
                    ShowUser(this).resumeWith(Result.success(user))
                }
                2 -> {
                    label = -1
                    continuation.resumeWith(Result.success(Unit))
                }
                else -> {
                    Error("Invalid state")
                }
            }
        }
        override val context: CoroutineContext = EmptyCoroutineContext
    }

    class FetchUser(private val continuation: Continuation<Any?>): Continuation<Any?> {

        override fun resumeWith(result: Result<Any?>) {
            val user = User ("Marcin")
            continuation.resumeWith(Result.success(user))
        }

        override val context: CoroutineContext = EmptyCoroutineContext
    }

    class ShowUser(
        private val continuation: Continuation<Any?>
    ): Continuation<Any?> {
        override val context: CoroutineContext = EmptyCoroutineContext

        override fun resumeWith(result: Result<Any?>) {
            println(result.getOrNull())
            continuation.resumeWith(Result.success(Unit))
        }
    }
}

// TODO implement root continuation - CompleteContinuation

fun main() {
    // TODO run continuation
}