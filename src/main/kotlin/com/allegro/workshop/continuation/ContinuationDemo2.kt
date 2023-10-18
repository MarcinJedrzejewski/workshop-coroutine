package com.allegro.workshop.continuation

import com.allegro.workshop.callback.User
import kotlin.coroutines.Continuation

class ContinuationDemo2 {

    // TODO suspending functions (CPS applied)

    fun fetchAndShowUser(cont: Continuation<Any?>) {
    }

    fun fetchUser(cont: Continuation<User?>): User {
        return User("Marcin")
    }

    fun showUser(user: User, cont: Continuation<User?>) {
    }
}

fun main() {

    // TODO  ContinuationDemo2().fetchAndShowUser(CompletionCallback())
}