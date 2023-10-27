package com.allegro.workshop.dispatch

import com.allegro.workshop.callback.User
import com.allegro.workshop.suspensionpoint.SuspensionPointDemo3
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.intrinsics.intercepted

private val workerPool = Executors.newScheduledThreadPool(1)

suspend fun doWork(user: User) {

    // use kotlin.coroutines.intrinsics.intercepted
    // TODO copy implementation from SuspensionPointDemo3, use a separated worker pool
    suspendCoroutineUninterceptedOrReturn { cont ->
        val work = {
            println("${Thread.currentThread()} | do some work for User: $user")
            cont.intercepted().resumeWith(Result.success(user))
        }
        workerPool.schedule(work, 5000, TimeUnit.MILLISECONDS)
        COROUTINE_SUSPENDED
    }
}

class DispatchDemo1 {

    suspend fun fetchAndShowUser() {
        println("${Thread.currentThread()} | fetchAndShowUser")

        val user = fetchUser()
        println("${Thread.currentThread()} | fetched user: $user")

        doWork(user)
        showUser(user)
    }

    suspend fun fetchUser(): User {
        return User("Marcin")
    }

    suspend fun showUser(user: User) {
        println("${Thread.currentThread()} | show $user")
    }
}

suspend fun main() {

    val dispatcher = Executors.newScheduledThreadPool(1)
    val ping = {
        println("${Thread.currentThread()} | ping ")
    }
    dispatcher.scheduleAtFixedRate(ping, 1000, 1000, TimeUnit.MILLISECONDS)

    withContext(dispatcher.asCoroutineDispatcher()) {
        DispatchDemo1().fetchAndShowUser()
    }
}