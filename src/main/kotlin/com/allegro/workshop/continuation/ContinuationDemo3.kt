package com.allegro.workshop.continuation

import kotlin.coroutines.suspendCoroutine


// TODO invoke continuation in suspend function
suspend fun handleUser() {
    suspendCoroutine { cont ->
        ContinuationDemo2().fetchAndShowUser(cont)
    }
}

suspend fun main() {
    handleUser()
}