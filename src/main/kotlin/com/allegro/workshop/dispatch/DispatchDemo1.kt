package com.allegro.workshop.dispatch

import com.allegro.workshop.callback.User


suspend fun doWork(user: User) {

    // use kotlin.coroutines.intrinsics.intercepted
    // TODO copy implementation from SuspensionPointDemo3, use a separated worker pool
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

    DispatchDemo1().fetchAndShowUser()
}