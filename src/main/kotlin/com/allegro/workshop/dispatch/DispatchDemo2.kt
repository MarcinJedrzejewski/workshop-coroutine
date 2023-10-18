package com.allegro.workshop.dispatch

class SuspensionPointDemo2 {

    //  suspend fun fetchAndShowUser() {
    //        val user = fetchUser()
    //        doWork(user)
    //        showUser(user)
    //    }
    // TODO copy SuspensionPointDemo2
}

// TODO create abstract base class BaseContinuationImpl for our continuations

// TODO create a new dispatcher and implement interface ContinuationInterceptor

// TODO create delegate DispatchedContinuation

// TODO implement ScopeContinuation and invoke SuspensionPointDemo2().fetchAndShowUser(this)

// TODO implement CompletionOrThrowCallback and set context

fun main() {
    // set worker pool to context
    // Executors.newScheduledThreadPool(2)

    println("suspended")
}
