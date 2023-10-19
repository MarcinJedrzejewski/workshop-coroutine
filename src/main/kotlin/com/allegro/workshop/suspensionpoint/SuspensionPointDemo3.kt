package com.allegro.workshop.suspensionpoint

import com.allegro.workshop.callback.User
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.intercepted
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

suspend fun doWork(user: User) {

    // TODO implement continuation in suspend function
    suspendCoroutineUninterceptedOrReturn { cont ->
        val work = {
            println("${Thread.currentThread()} | do some work for User: $user")
            cont.resumeWith(Result.success(user))
        }
        workerPool.schedule(work, 5000, TimeUnit.MILLISECONDS)
        COROUTINE_SUSPENDED
    }
}

private val workerPool = Executors.newScheduledThreadPool(1)

class SuspensionPointDemo3 {

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
    // TODO create new dispatcher from Executors.newScheduledThreadPool add ping task

    val dispatcher = Executors.newScheduledThreadPool(1)
    val ping = {
        println("${Thread.currentThread()} | ping")
    }
    dispatcher.scheduleAtFixedRate(ping,1000, 1000, TimeUnit.MILLISECONDS)


    // TODO use withContext to change context
    withContext(dispatcher.asCoroutineDispatcher()) {
        SuspensionPointDemo3().fetchAndShowUser()
    }
}