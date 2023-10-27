package com.allegro.workshop.pause

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

// Coroutine context element for disable pausing in a part of coroutine
object NonPausing : AbstractCoroutineContextElement(Key) {

    private object Key : CoroutineContext.Key<NonPausing>
}