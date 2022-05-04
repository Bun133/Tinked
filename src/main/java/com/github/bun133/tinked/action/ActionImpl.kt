package com.github.bun133.tinked.action

import com.github.bun133.tinked.task.TickedTask

class ActionImpl<R : Any>(val task: TickedTask<R>, val atOnce: () -> R?) : Action<R> {
    override fun toTickedTask(): TickedTask<R> {
        return task
    }

    override fun executeAtOnce(): R? {
        return atOnce()
    }
}