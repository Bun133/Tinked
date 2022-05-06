package com.github.bun133.tinked.task.builder

import com.github.bun133.tinked.task.Task

fun test() {
    val task = Task<Unit, String> {
        "I'm a task"
    }

    val task2 = Task<String, String> {
        "Hello, ${it.substring(6..9)}!"
    }

    val task3 = task.chain(task2)

    println(task3.run(Unit))
}

/**
 * Chain two tasks,converting into one task.
 */
fun <I, T, R> Task<I, T>.chain(other: Task<T, R>): Task<I, R> {
    return Task {
        other.run(this.run(it))
    }
}

/**
 * Run two tasks(the result of first task is ignored),converting into one task.
 */
fun <I, R> Task<I, *>.also(other: Task<Unit, R>): Task<I, R> {
    return Task {
        this.run(it)
        other.run(Unit)
    }
}