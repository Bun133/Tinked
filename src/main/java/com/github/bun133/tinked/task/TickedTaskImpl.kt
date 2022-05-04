package com.github.bun133.tinked.task

import com.github.bun133.tinked.utils.Future

class TickedTaskImpl<R : Any>(
    override val consumeTick: Int,
    val run: (Int) -> R?,
    private val runner: TickedTaskRunner
) : TickedTask<R>() {
    private var future: Future<R>? = null

    override fun run(currentTick: Int, isLastTick: Boolean): R? {
        val r = run(currentTick)
        if (isLastTick) {
            future?.setResult(r)
        }

        return r
    }

    override fun cancel() {
        future = null
        runner.cancel(this)
    }

    override fun start(): Future<R> {
        initFuture()
        runner.run(this)
        return future!!
    }

    private fun initFuture() {
        if (future == null) {
            future = Future<R>()
        }
    }
}