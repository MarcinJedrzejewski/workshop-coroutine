package com.allegro.workshop.continuation

import com.allegro.workshop.callback.User

class SuspendDemo {
    suspend fun fetchAndShowUser() {
        val user = fetchUser()
        showUser(user)
    }

    suspend fun fetchUser(): User {
        return User("Marcin")
    }

    suspend fun showUser(user: User) {
        println(user)
    }
}

suspend fun main() {
    val demo = SuspendDemo()
    demo.fetchAndShowUser()
}