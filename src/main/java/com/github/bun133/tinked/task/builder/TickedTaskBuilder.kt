package com.github.bun133.tinked.task.builder

import com.github.bun133.tinked.task.Task
import com.github.bun133.tinked.task.TickedTask
import com.github.bun133.tinked.task.TickedTaskImpl
import com.github.bun133.tinked.task.TickedTaskRunner

/**
 * A builder for [TickedTask],
 * For get a instance of [TickedTaskRunner],
 * @see [TickedTaskRunner.instance]
 */
fun <I : Any, R : Any> TickedTaskRunner.tickedTask(vararg tasks: Task<*, *>): TickedTask<I, R> {
    return TickedTaskImpl<I,R>(tasks)
}