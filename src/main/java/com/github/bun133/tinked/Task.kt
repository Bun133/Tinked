package com.github.bun133.tinked

abstract class Task<I, T>() {
    protected var nextNode: Task<T, *>? = null

    /**
     * このタスクともう一つのタスクをチェーンする
     */
    fun <R : Any> then(other: Task<T, R>): Task<T, R> {
        nextNode = other
        return other
    }

    /**
     * このタスクともう一つのタスクを一つのタスクにする
     */
    open fun <V : Any> apply(other: Task<T, V>): Task<I, V> {
        return ApplyedTask(this, other)
    }

    /**
     * Run this task with the given input, and call next task with the result.
     */
    open fun run(input: I) {
        val result = runnable(input)
        nextNode?.run(result)
    }

    protected abstract fun runnable(input: I): T

    companion object {
        /**
         * JSのPromise.allと同じようなタスクを作る
         */
        fun <I, R> all(vararg task: Task<I, R>): Task<I, List<R>> {
            return AllTask<I, R>(task.toList())
        }

        fun <I, R> forEach(task: () -> Task<I, R>): Task<List<I>, Unit> {
            return ForEachTask(task)
        }
    }
}