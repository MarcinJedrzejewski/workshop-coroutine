package com.allegro.workshop.callback

class CallbackDemo {

    // TODO implement callbacks

    fun fetchAndShowUser() {
    }

    fun fetchUser(): User {
        return User("Marcin")
    }

    fun showUser(user: User,) {
        println(user)
    }

    fun removeUser(user: User) {
        println("remove user : $user")
    }
}

fun main() {
    val demo = CallbackDemo()
    demo.fetchAndShowUser()
}