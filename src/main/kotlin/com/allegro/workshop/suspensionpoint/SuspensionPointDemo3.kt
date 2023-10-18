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
}

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

    // TODO use withContext to change context
    SuspensionPointDemo3().fetchAndShowUser()
}