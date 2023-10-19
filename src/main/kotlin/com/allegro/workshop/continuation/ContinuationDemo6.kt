package com.allegro.workshop.continuation

import com.allegro.workshop.callback.User
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class ContinuationDemo6 {

    //   TODO Copy ContinuationDemo5 nad implement a state machine with a loop
    //
    //   suspend fun fetchAndShowUser() {
    //        for (i in 1..10) {
    //            val user = fetchUser(i)
    //            showUser(user)
    //        }
    //    }

    fun fetchAndShowUser(cont: Continuation<Any?>) {
        class FetchAndShowUser(private val continuation: Continuation<Any?>) : Continuation<Any?> {
            var label = 0
            var res: Result<Any?>? = null
            var completed = false
            var counter = 0

            override fun resumeWith(result: Result<Any?>) {
                this.res = result
                val outcome = try {
                    fetchAndShowUser(this)
                    Result.success(Unit)
                } catch (e : Throwable) {
                    Result.failure(e)
                }

                if (!completed) {
                    continuation.resumeWith(outcome)
                    completed = true
                }
            }

            override val context: CoroutineContext = EmptyCoroutineContext
        }

        val end = 10
        val localContinuation = if (cont is FetchAndShowUser) cont else FetchAndShowUser(cont)
        when (localContinuation.label) {
            0 -> {
                localContinuation.counter = 1
                localContinuation.label = 1
                localContinuation.resumeWith(Result.success(Unit))
            }
            1 -> {
                val i = localContinuation.counter
                if (i > end)
                    return
                localContinuation.label = 2
                fetchUser(localContinuation)
            }
            2 -> {
                val i = localContinuation.counter
                localContinuation.label = 1
                localContinuation.counter = i + 1
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
                val outcome = try {
                    val user = fetchUser(this)
                    Result.success(user)
                } catch (e: Throwable) {
                    Result.failure(e)
                }

                continuation.resumeWith(outcome)
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
                val outcome = try {
                    showUser(result.getOrNull() as User, this)
                    Result.success(null)
                } catch (e : Throwable) {
                    Result.failure(e)
                }
                continuation.resumeWith(outcome)
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
    ContinuationDemo6().fetchAndShowUser(CompletionOrThrowCallback())
}
