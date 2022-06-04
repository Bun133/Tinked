package com.github.bun133.tinked

/**
 * Runnable Task
 */
open class RunnableTask<I, R>(val r: (I) -> R) : Task<I, R>() {
    override fun runnable(input: I): R {
        return r(input)
    }
}