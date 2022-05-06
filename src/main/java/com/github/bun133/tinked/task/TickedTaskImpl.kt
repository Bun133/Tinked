package com.github.bun133.tinked.task

import com.github.bun133.tinked.utils.Future

class TickedTaskImpl<I : Any, R : Any>(
    val tasks: List<Task<*, *>>,
    override val consumeTick: Int = tasks.size,
    private val runner: TickedTaskRunner
) : TickedTask<I, R>() {
    private var future: Future<R>? = null

    override fun cancel() {
        future = null
        runner.cancel(this)
    }

    override fun start(i: I): Future<R> {
        initFuture()
        runner.run(i, this)
        return future!!
    }

    private fun initFuture() {
        if (future == null) {
            future = Future<R>()
        }
    }
}