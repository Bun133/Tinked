package com.github.bun133.tinked

/**
 * JSのPromise.allみたいなやつ
 */
class AllTask<I, R>(val tasks: List<Task<I, R>>) : Task<I, List<R>>() {
    override fun runnable(input: I): List<R> {
        throw UnsupportedOperationException()
    }

    private val results = mutableMapOf<Task<I, R>, R>()

    override fun run(input: I) {
        results.clear()
        tasks.forEach {
            it.apply(RunnableTask { r -> checkFinished(it, r) }).run(input)
        }
    }

    private fun checkFinished(task: Task<I, R>, r: R) {
        results[task] = r
        if (results.size == tasks.size) {
            // すべて終わった
            nextNode?.run(results.values.toList())
            results.clear()
        }
    }
}