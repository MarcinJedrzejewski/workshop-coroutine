package com.allegro.workshop.cooperation

class CooperationDemo {

    // TODO change functions to make cooperate between them

    fun functionA() {
        taskA1()
        taskA2()
        taskA3()
        taskA4()
    }

    fun functionB() {
        taskB1()
        taskB2()
        taskB3()
        taskB4()
    }

    private fun taskA1() { println("Task A1") }
    private fun taskA2() { println("Task A2") }
    private fun taskA3() { println("Task A3") }
    private fun taskA4() { println("Task A4") }

    private fun taskB1() { println("Task B1") }
    private fun taskB2() { println("Task B2") }
    private fun taskB3() { println("Task B3") }
    private fun taskB4() { println("Task B4") }
}

fun main() {
    val demo = CooperationDemo()
    demo.functionA()
    demo.functionB()
}