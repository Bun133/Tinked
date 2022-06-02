package com.github.bun133.tinked

/**
 * Runnable Task
 */
open class RunnableTask<I, R>(val r: RunnableTaskContext<I, R>.(I) -> R) : Task<I, R>() {
    override fun runnable(input: I): R {
        return RunnableTaskContext(this).r(input)
    }
}

/**
 * Runnable TaskのContext
 */
class RunnableTaskContext<I, R>(private val task: RunnableTask<I, R>) {
    /**
     * このタスクの後に実行するタスクを設定する
     */
    fun <T : Any> join(otherTask: Task<R, T>) {
        task.then(otherTask)
    }
}