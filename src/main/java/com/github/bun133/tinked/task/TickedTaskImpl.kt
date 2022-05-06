package com.github.bun133.tinked.task

import com.github.bun133.tinked.utils.Future

class TickedTaskImpl<I : Any, R : Any>(start: TickedTaskNodeImpl<I, *>, private val runner: TickedTaskRunner) :
    TickedTask<I, R>() {
    override val consumeTick: Int = start.getAllTree().size

    override fun cancel() {
        runner.cancel(this)
    }

    override fun start(i: I): Future<R> {
        return runner.run(i, this)
    }
}