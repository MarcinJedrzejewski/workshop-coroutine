package com.allegro.workshop.dispatch

import com.allegro.workshop.callback.User
import com.allegro.workshop.continuation.CompletionOrThrowCallback
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED

private val workerPool = Executors.newScheduledThreadPool(1)

class DispatchDemo2 {

    //  suspend fun fetchAndShowUser() {
    //        val user = fetchUser()
    //        doWork(user)
    //        showUser(user)
    //    }
    // TODO copy SuspensionPointDemo2

    fun fetchAndShowUser(cont: Continuation<Any?>): Any {
        class FetchAndShowUser(private val continuation: Continuation<Any?>) : Continuation<Any?> {
            var label = 0
            var res: Result<Any?>? = null

            var user: User? = null

            override fun resumeWith(result: Result<Any?>) {
                this.res = result
                val outcome = try {
                    val r = fetchAndShowUser(this)
                    if (r == COROUTINE_SUSPENDED) {
                        return
                    }
                    Result.success(Unit)
                } catch (e : Throwable) {
                    Result.failure(e)
                }

                continuation.resumeWith(outcome)
            }

            override val context: CoroutineContext = EmptyCoroutineContext
        }

        val localContinuation = if (cont is FetchAndShowUser) cont else FetchAndShowUser(cont)
        when (localContinuation.label) {
            0 -> {
                localContinuation.label = 1
                val res = fetchUser(localContinuation)
                if (res == COROUTINE_SUSPENDED) {
                    return COROUTINE_SUSPENDED
                }
            }
            1 -> {
                localContinuation.label = 2
                val user = localContinuation.res!!.getOrNull() as User
                localContinuation.user = user
                val res = doWork(user, localContinuation)
                if (res == COROUTINE_SUSPENDED) {
                    return COROUTINE_SUSPENDED
                }
            }
            2 -> {
                localContinuation.label = 3
                val user = localContinuation.user!!
                val res = showUser(user, localContinuation)
                if (res == COROUTINE_SUSPENDED) {
                    return COROUTINE_SUSPENDED
                }
            }
            else -> {
                return Unit
            }
        }
        return Unit
    }

    fun fetchUser(cont: Continuation<User?>): Any {

        class FetchUser(private val continuation: Continuation<User?>): Continuation<User?> {

            var label = 0
            var res: Result<Any?>? = null

            override fun resumeWith(result: Result<User?>) {
                res = result
                val outcome = try {
                    val r = fetchUser(this)
                    if (r == COROUTINE_SUSPENDED) {
                        return
                    }
                    Result.success(r as User)
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

    fun showUser(user: User, cont: Continuation<User?>): Any {
        class ShowUser(private val continuation: Continuation<User?>): Continuation<User?> {
            var label = 0
            var res: Result<Any?>? = null

            override fun resumeWith(result: Result<User?>) {
                res = result
                val outcome = try {
                    val r = showUser(result.getOrNull() as User, this)
                    if (r == COROUTINE_SUSPENDED) {
                        return
                    }
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
                println("${Thread.currentThread()} | $user")
                localContinuation.resumeWith(Result.success(user))
            }
            else -> {
                return Unit
            }
        }

        return Unit
    }

    fun doWork(user: User, cont: Continuation<User?>): Any {
        class DoWork(private val continuation: Continuation<User?>): Continuation<User?> {
            var label = 0
            var res: Result<Any?>? = null

            override fun resumeWith(result: Result<User?>) {
                res = result
                val outcome = try {
                    val r = doWork(result.getOrNull()!!, this)
                    if (r == COROUTINE_SUSPENDED) {
                        return
                    }
                    Result.success(null)
                } catch (e: Throwable) {
                    Result.failure(e)
                }
                continuation.resumeWith(outcome)
            }

            override val context: CoroutineContext = EmptyCoroutineContext
        }

        val localContinuation = if (cont is DoWork) cont else DoWork(cont)

        when (localContinuation.label) {
            0 -> {
                localContinuation.label = 1
                return localContinuation.resumeWith(Result.success(user))
            }
            1 -> {
                localContinuation.label = 2
                val work = {
                    println("${Thread.currentThread()} | do some work for User: $user")
                    localContinuation.resumeWith(Result.success(user))
                }
                workerPool.schedule(work, 5000, TimeUnit.MILLISECONDS)
                return COROUTINE_SUSPENDED
            }
            else -> {
                return Unit
            }
        }
    }
}

// TODO create abstract base class BaseContinuationImpl for our continuations

// TODO create a new dispatcher and implement interface ContinuationInterceptor

// TODO create delegate DispatchedContinuation

// TODO implement ScopeContinuation and invoke SuspensionPointDemo2().fetchAndShowUser(this)

// TODO implement CompletionOrThrowCallback and set context

fun main() {
    // set worker pool to context
    // Executors.newScheduledThreadPool(2)

    DispatchDemo2().fetchAndShowUser(CompletionOrThrowCallback())
    println("${Thread.currentThread()} | suspended")
}
