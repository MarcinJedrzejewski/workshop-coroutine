package com.allegro.workshop.context

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class CounterContext(
    private val name: String
) : CoroutineContext.Element {

    override val key: CoroutineContext.Key<*> = Key

    private var counter  = 0

    fun printCounter() {
        println("$name : $counter")
        counter++
    }

    companion object Key : CoroutineContext.Key<CounterContext>
}

suspend fun printNext() {
   // TODO find counter in coroutine context
   coroutineContext[CounterContext]?.printCounter()
}

suspend fun main() {
    // TODO set counter in context
    withContext(CounterContext("Outer")) {
        printNext()
        launch {
            printNext()
            launch {
                printNext()
            }
            launch(CounterContext("Inner")) {
                printNext()
                launch {
                    printNext()
                }
            }
        }
        printNext()
    }
}