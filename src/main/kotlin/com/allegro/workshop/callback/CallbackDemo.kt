package com.allegro.workshop.callback

class CallbackDemo {

    // TODO implement callbacks

    fun fetchAndShowUser() {
        fetchUser { user ->
            showUser(user) {
                removeUser(it){
                    println("All done")
                }
            }
        }
    }

    fun fetchUser(callback: (User) -> Unit): User {
        val user = User("Marcin")
        callback(user)
        return user
    }

    fun showUser(user: User, callback: (User) -> Unit) {
        println(user)
        callback(user)
    }

    fun removeUser(user: User,  callback: (User) -> Unit) {
        println("remove user : $user")
        callback(user)
    }
}

fun main() {
    val demo = CallbackDemo()
    demo.fetchAndShowUser()
}