package com.allegro.workshop.continuation

import com.allegro.workshop.callback.User
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class ContinuationDemo2 {

    // TODO suspending functions (CPS applied)

    fun fetchAndShowUser(cont: Continuation<Any?>) {
        class FetchAndShowUser(private val continuation: Continuation<Any?>) : Continuation<Any?> {
            var label = 0
            var res: Result<Any?>? = null
            var completed = false

            override fun resumeWith(result: Result<Any?>) {
                this.res = result
                fetchAndShowUser(this)
                if (!completed) {
                    continuation.resumeWith(Result.success(Unit))
                    completed = true
                }
            }

            override val context: CoroutineContext = EmptyCoroutineContext
        }

        val localContinuation = if (cont is FetchAndShowUser) cont else FetchAndShowUser(cont)
        when (localContinuation.label) {
            0 -> {
                localContinuation.label = 1
                fetchUser(localContinuation)
            }
            1 -> {
                localContinuation.label = 2
                val user = localContinuation.res!!.getOrNull() as User
                showUser(user, localContinuation)
            }
            else -> {
                return
            }
        }
    }

    fun fetchUser(cont: Continuation<User?>): User {

        class FetchUser(private val continuation: Continuation<User?>): Continuation<User?> {

            var label = 0
            var res: Result<Any?>? = null

            override fun resumeWith(result: Result<User?>) {
                res = result
                val user = fetchUser(this)
                continuation.resumeWith(Result.success(user))
            }

            override val context: CoroutineContext = EmptyCoroutineContext
        }

        val localContinuation = if (cont is FetchUser) cont else FetchUser(cont)

        when (localContinuation.label) {
            0 -> {
                localContinuation.label = 1
                val user = User("Marcin")
                localContinuation.resumeWith(Result.success(user))
            }
            else -> {
                return localContinuation.res!!.getOrThrow() as User
            }
        }

        return localContinuation.res?.getOrThrow() as User
    }

    fun showUser(user: User, cont: Continuation<User?>) {
        class ShowUser(private val continuation: Continuation<User?>): Continuation<User?> {
            var label = 0
            var res: Result<Any?>? = null

            override fun resumeWith(result: Result<User?>) {
                res = result
                showUser(result.getOrNull() as User, this)
                continuation.resumeWith(Result.success(null))
            }

            override val context: CoroutineContext = EmptyCoroutineContext
        }

        val localContinuation = if (cont is ShowUser) cont else ShowUser(cont)

        when (localContinuation.label) {
            0 -> {
                localContinuation.label = 1
                println(user)
                localContinuation.resumeWith(Result.success(user))
            }
            else -> {
                return
            }
        }
    }
}

fun main() {

    ContinuationDemo2().fetchAndShowUser(CompletionCallback())
}