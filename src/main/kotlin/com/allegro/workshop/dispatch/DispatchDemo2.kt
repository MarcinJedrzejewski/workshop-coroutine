package com.allegro.workshop.dispatch

import com.allegro.workshop.callback.User
import com.allegro.workshop.continuation.CompletionOrThrowCallback
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
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
        class FetchAndShowUser(continuation: Continuation<Any?>): BaseContinuationImpl(continuation) {
            var label = 0
            var res: Result<Any?>? = null
            var user: User? = null

            override fun invokeSuspend(result: Result<Any?>): Any? {
                this.res = result
                return fetchAndShowUser(this)
            }
        }

        val localContinuation = if (cont is FetchAndShowUser) cont else FetchAndShowUser(cont)

        val result: Result<Any?>? = localContinuation.res
        when (localContinuation.label) {
            0 -> {
                localContinuation.label = 1
                println("${Thread.currentThread()} | fetchAndShowUser")
                val res = fetchUser(localContinuation)
                if (res == COROUTINE_SUSPENDED) {
                    return COROUTINE_SUSPENDED
                }
            }
            1 -> {
                val user = result!!.getOrThrow()!! as User
                localContinuation.label = 2
                localContinuation.user = user
                println("${Thread.currentThread()} | doWork")
                val res = doWork(user, localContinuation)
                if (res == COROUTINE_SUSPENDED) {
                    return COROUTINE_SUSPENDED
                }
            }
            2 -> {
                localContinuation.label = 3
                val user = localContinuation.user!!
                println("${Thread.currentThread()} | showUser")
                val res = showUser(user, localContinuation)
                if (res == COROUTINE_SUSPENDED) {
                    return COROUTINE_SUSPENDED
                }
            }
            else -> {
                result!!.getOrThrow()
                return Unit
            }
        }
        return Unit
    }


    // suspending functions (CPS applied)
    fun fetchUser(cont: Continuation<Any?>): Any? {
        class FetchUser(continuation: Continuation<Any?>): BaseContinuationImpl(continuation) {
            var label = 0
            var res: Result<Any?>? = null

            override fun invokeSuspend(result: Result<Any?>): Any? {
                res = result
                return fetchUser(this)
            }
        }

        val localContinuation = if (cont is FetchUser) cont else FetchUser(cont)

        when (localContinuation.label) {
            0 -> {
                localContinuation.label = 1
                val user = User("Marcin")
                localContinuation.resumeWith(Result.success(user))
            }
            else -> {
                return localContinuation.res?.getOrThrow()
            }
        }

        return localContinuation.res?.getOrThrow()
    }

    // suspending functions (CPS applied)
    fun showUser(user: User, cont: Continuation<Any?>): Any {
        class ShowUser(continuation: Continuation<Any?>): BaseContinuationImpl(continuation) {
            var label = 0
            var res: Result<Any?>? = null

            override fun invokeSuspend(result: Result<Any?>): Any?{
                res = result
                return showUser(result.getOrNull()!! as User, this)
            }
        }

        val localContinuation = if (cont is ShowUser) cont else ShowUser(cont)

        when (localContinuation.label) {
            0 -> {
                localContinuation.label = 1
                println("${Thread.currentThread()} | println user")
                println(user)
                localContinuation.resumeWith(Result.success(user))
            }
            else -> {
                return Unit
            }
        }

        return Unit
    }

    fun doWork(user: User, cont: Continuation<Any?>): Any {
        class DoWork(continuation: Continuation<Any?>): BaseContinuationImpl(continuation) {
            var label = 0
            var res: Result<Any?>? = null

            override fun invokeSuspend(result: Result<Any?>): Any? {
                res = result
                return doWork(result.getOrNull()!! as User, this)
            }
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
                    localContinuation.intercepted().resumeWith(Result.success(user))
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
abstract class BaseContinuationImpl(private val continuation: Continuation<Any?>) : Continuation<Any?> {

    override val context: CoroutineContext
        get() = continuation.context

    private var intercepted: Continuation<Any?>? = null

    fun intercepted(): Continuation<Any?> =
        intercepted ?: (context[ContinuationInterceptor]?.interceptContinuation(this) ?: this)
            .also { intercepted = it }

    final override fun resumeWith(result: Result<Any?>) {
        val res = result
        val outcome = try {
            val r = invokeSuspend(res)
            if (r == COROUTINE_SUSPENDED) {
                return
            }
            Result.success(r)
        } catch (e: Throwable) {
            Result.failure(e)
        }
        continuation.resumeWith(outcome)
    }

    abstract fun invokeSuspend(result: Result<Any?>): Any?
}

class DispatcherTest(
    private val executorService: ExecutorService
) : ContinuationInterceptor {

    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor.Key

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatchedContinuation(this, continuation)
    }

    fun dispatch(task: Runnable) {
        executorService.submit(task)
    }
}

class DispatchedContinuation<T>(
    private val dispatcherTest: DispatcherTest,
    val continuation: Continuation<T>
) : Continuation<T> by continuation {
    override fun resumeWith(result: Result<T>) {
        dispatcherTest.dispatch {
            continuation.resumeWith(result)
        }
    }
}

class ScopeCoroutine(ctx: CoroutineContext): BaseContinuationImpl(CompletionOrThrowCallback(ctx)) {
    private var label = 0
    private var res: Result<Any?>? = null

    override fun invokeSuspend(result: Result<Any?>): Any? {
        res = result
        return when (label) {
            0 -> {
                label = 1
                DispatchDemo2().fetchAndShowUser(this)
            }
            else -> {
                res?.getOrThrow()
            }
        }
    }
}


// TODO implement ScopeContinuation and invoke SuspensionPointDemo2().fetchAndShowUser(this)

class CompletionOrThrowCallback(ctx: CoroutineContext): Continuation<Any?>{
    override fun resumeWith(result: Result<Any?>) {
        println("${Thread.currentThread()} We are done now: $result")
        result.getOrThrow()
    }

    override val context: CoroutineContext = ctx
}

fun main() {
    // set worker pool to context
    val executor = Executors.newScheduledThreadPool(2)

    ScopeCoroutine(DispatcherTest(executor)).intercepted()
        .resumeWith(Result.success(Unit))

    println("${Thread.currentThread()} | suspended")
}
