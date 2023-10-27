package com.allegro.workshop.exception

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {

        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("${throwable.message}")
        }

        //TODO set SupervisorJob
        val parentScope = CoroutineScope(Job() + Dispatchers.IO )

        val job1 = parentScope.launch {
            delay(150)
            //throw Exception("Something went wrong")
        }

        val job2 = parentScope.launch {
        }

        val deferred = parentScope.async {
            delay(100)
            // TODO throw exception
        }

        delay(300)

        // TODO add await

        println("Job1: isCancelled = ${job1.isCancelled} ; isCompleted = ${job1.isCompleted}")
        println("Job2: isCancelled = ${job2.isCancelled} ; isCompleted = ${job2.isCompleted}")
        println("Deffered: isCancelled = ${deferred.isCancelled} ; isCompleted = ${deferred.isCompleted}")
        println("ParentScope: isCancelled = ${parentScope.coroutineContext[Job]!!.isCancelled} ; isCompleted = ${parentScope.coroutineContext[Job]!!.isCompleted}")
    }
}