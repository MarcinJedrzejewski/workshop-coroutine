package com.allegro.workshop.continuation

import com.allegro.workshop.callback.User
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


class ContinuationDemo5 {

    // TODO extend implementation ContinuationDemo2 by exception handling
    fun fetchAndShowUser(cont: Continuation<Any?>) {
        class FetchAndShowUser(private val continuation: Continuation<Any?>) : Continuation<Any?> {
            var label = 0
            var res: Result<Any?>? = null
            var completed = false

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

// TODO implement CompletionOrThrowCallback
class CompletionOrThrowCallback: Continuation<Any?>{
    override fun resumeWith(result: Result<Any?>) {
        println("We are done now: $result")
        result.getOrThrow()
    }

    override val context: CoroutineContext = EmptyCoroutineContext
}

fun main() {
    ContinuationDemo5().fetchAndShowUser(CompletionOrThrowCallback())
}
