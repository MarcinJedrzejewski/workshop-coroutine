package com.allegro.workshop.context

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class CounterContext(
    private val name: String
) {
    // TODO create context element
}

suspend fun printNext() {
   // TODO find counter in coroutine context
}

suspend fun main() {
    // TODO set counter in context
}