package com.github.bun133.tinked

/**
 * すべての入力に対してthenで実行を続けるタスク
 */
class ForEachTask<T, R>(private val realTask: () -> Task<T, R>) : Task<List<T>, Unit>() {
    override fun runnable(input: List<T>) {
        throw UnsupportedOperationException()
    }

    private val inputs = mutableListOf<T>()
    private var index = 0

    override fun run(input: List<T>) {
        inputs.clear()
        inputs.addAll(input)
        index = 0
        run()
    }

    private fun run() {
        val task = realTask()
        val input = inputs.getOrNull(index)
        if (input != null) {
            task.apply {
                then(RunnableTask { index++;run() })
            }.run(input)
        } else {
            // 終了
            nextNode?.run(Unit)
        }
    }
}