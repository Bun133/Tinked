package com.github.bun133.tinked

class NotNullTask<T : Any>(private val toThrowException: Boolean = true) : Task<T?, T>() {
    override fun runnable(input: T?): T {
        // This code is unreachable because of the type system.
        throw NotImplementedError("Not yet implemented")
    }

    override fun run(input: T?) {
        if (input == null) {
            if (toThrowException) {
                throw NullPointerException()
            } else {
                return
            }
        } else {
            nextNode?.run(input)
        }
    }
}