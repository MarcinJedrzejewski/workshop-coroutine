package com.allegro.workshop.continuation

import com.allegro.workshop.callback.User
import kotlin.coroutines.intrinsics.createCoroutineUnintercepted

fun main() {

    // Define a suspend function that returns user
    suspend fun fetchUser(): User {
        return User("Marcin")
    }

    val f = ::fetchUser
    val cont = f.createCoroutineUnintercepted(CompletionCallback())
    cont.resumeWith(Result.success(Unit))
}