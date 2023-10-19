package com.allegro.workshop.cooperation

class CooperationDemo {

    // TODO change functions to make cooperate between them

    fun functionA(label: Int) {
        when (label) {
            1 -> {
                taskA1()
                functionB(1)
            }
            2 -> {
                taskA2()
                functionB(2)
            }
            3 -> {
                taskA3()
                functionB(3)
            }
            4 -> {
                taskA4()
                functionB(4)
            }
        }
    }

    fun functionB(label: Int) {
        when(label) {
            1 -> {
                taskB1()
                functionA(2)
            }
            2 -> {
                taskB2()
                functionA(3)
            }
            3-> {
                taskB3()
                functionA(4)
            }
            4 -> {
                taskB4()
            }
        }
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
    demo.functionA(1)
}