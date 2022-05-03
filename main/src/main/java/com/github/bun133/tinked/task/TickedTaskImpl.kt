package com.github.bun133.tinked.task

class TickedTaskImpl<R : Any>(override val consumeTick: Int, val run: (Int) -> R?) : TickedTask<R>() {
    override fun run(currentTick: Int, isLastTick: Boolean): R? {
        return run(currentTick)
    }
}