package com.allegro.workshop.continuation

import com.allegro.workshop.callback.User

fun main() {

    // Define a suspend function that returns user
    suspend fun fetchUser(): User {
        return User("Marcin")
    }

    // TODO Create and start the coroutine
}