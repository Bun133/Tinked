package com.github.bun133.tinked

abstract class Task<I , T >() {
    protected var nextNode: Task<T, *>? = null
    fun <R : Any> then(other: Task<T, R>): Task<T, R> {
        nextNode = other
        return other
    }

    /**
     * Run this task with the given input, and call next task with the result.
     */
    open fun run(input: I) {
        val result = runnable(input)
        nextNode?.run(result)
    }

    abstract fun runnable(input: I): T
}