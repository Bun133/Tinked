package com.github.bun133.tinked.action

import com.github.bun133.tinked.task.TickedTask

class ActionImpl<I, R : Any>(val task: TickedTask<I, R>, val atOnce: (I) -> R?) : Action<I, R> {
    override fun toTickedTask(): TickedTask<I, R> {
        return task
    }

    override fun executeAtOnce(i: I): R? {
        return atOnce(i)
    }
}