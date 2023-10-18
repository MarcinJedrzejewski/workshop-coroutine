package com.allegro.workshop.callback

class DirectDemo {
    fun fetchAndShowUser() {
        val user = fetchUser()
        showUser(user)
    }

    fun fetchUser(): User {
        return User("Marcin")
    }

    fun showUser(user: User) {
        println(user)
    }
}

fun main() {
    val demo = DirectDemo()
    demo.fetchAndShowUser()
}

data class User(
    val name: String
)