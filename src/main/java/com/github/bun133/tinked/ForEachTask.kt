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
        inputs.addAll(input)
        index = 0
        inputs.clear()
        run()
    }

    private fun run() {
        val task = realTask()
        val input = inputs.getOrNull(index)
        if (input != null) {
            task.run(input)
                .apply {
                    then(RunnableTask { run() })
                }
        } else {
            // 終了
        }
    }
}