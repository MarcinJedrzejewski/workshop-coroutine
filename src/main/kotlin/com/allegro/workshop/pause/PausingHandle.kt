package com.allegro.workshop.pause

// TODO add methods pause, resume
interface PausingHandle {
    fun pause()

    fun resume()

    val isPaused: Boolean
}