package com.github.bun133.tinked

/**
 * Runnable Task
 */
open class RunnableTask<I, R>(val r: RunnableTaskContext<I, R>.(I) -> R) : Task<I, R>() {
    override fun runnable(input: I): R {
        throw UnsupportedOperationException("runnable task is not supported")
    }

    override fun run(input: I) {
        val ctx = RunnableTaskContext(this, input)
        ctx.run()
    }

    internal fun getNextNode() = nextNode
}

/**
 * Runnable TaskのContext
 */
class RunnableTaskContext<I, R>(private val task: RunnableTask<I, R>, private val input: I) {

    private val toApply = mutableListOf<Task<R, *>>()

    /**
     * このタスクの後に実行するタスクを設定する
     * ただし、返り値は無視される
     * このメソッドを介して追加されたタスクはこのタスク終了後、逐次実行される
     */
    fun apply(otherTask: Task<R, *>) {
        toApply.add(otherTask)
    }

    internal fun run() {
        val r = task.r(this, input)
        toApply.forEach { it.run(r) }
        task.getNextNode()?.run(r)
    }
}