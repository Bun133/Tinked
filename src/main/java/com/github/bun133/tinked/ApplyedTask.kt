package com.github.bun133.tinked

/**
 * 二つのタスクを一つにまとめるタスク
 */
open class ApplyedTask<I, V, R : Any>(val one: Task<I, V>, val two: Task<V, R>) : Task<I, R>() {
    override fun runnable(input: I): R {
        throw UnsupportedOperationException()
    }

    override fun run(input: I) {
        one.apply {
            then(two)
                .then(RunnableTask { nextNode?.run(it);Unit })
        }
    }
}

class TickedApplyedTask<I, V, R : Any>(val one: Task<I, V>, val two: Task<V, R>) : TickedTask<I, R>() {
    override fun runnable(input: I): R {
        throw UnsupportedOperationException()
    }

    override fun run(input: I) {
        one.apply {
            then(two)
                .then(RunnableTask { nextNode?.run(it);Unit })
        }
    }
}