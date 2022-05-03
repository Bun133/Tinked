package com.github.bun133.tinked.task.builder

import com.github.bun133.tinked.task.Task
import com.github.bun133.tinked.task.TickedTask
import com.github.bun133.tinked.task.TickedTaskImpl

fun <R : Any> tickedTask(b: TickedTaskBuilder<R>.() -> Unit): TickedTask<R> {
    val builder = TickedTaskBuilder<R>()
    builder.b()
    return builder.build()
}

class TickedTaskBuilder<R : Any> {
    private val notLast = mutableListOf<Pair<Task<*>, Int>>()
    private var last: Pair<Task<R>, Int>? = null

    internal fun build(): TickedTask<R> {
        if (last == null) {
            throw IllegalStateException("last task is not set")
        }

        val allCount = notLast.sumOf { it.second } + last!!.second
        val arr = arrayOfNulls<Task<*>>(allCount)
        var currentIndex = 0
        notLast.forEach { p ->
            repeat(p.second) {
                arr[currentIndex++] = p.first
            }
        }

        repeat(last!!.second) {
            arr[currentIndex++] = last!!.first
        }

        return TickedTaskImpl<R>(allCount) {
            if (it != allCount) {
                arr[it]!!.run()
                return@TickedTaskImpl null
            } else {
                return@TickedTaskImpl last!!.first.run()
            }
        }
    }

    /**
     * This method is to add a task to the builder chain.
     * IF THE TASK *IS* FINAL TASK, THIS METHOD SHOULD *NOT* BE CALLED.
     *  @see [addLast]
     *
     * @note TickedTask Should not add to this builder
     */
    fun addNotLast(t: Task<*>, consumeTick: Int) {
        notLast.add(t to consumeTick)
    }

    /**
     * This method is to add a task to the builder chain.
     * IF THE TASK IS *NOT* FINAL TASK, THIS METHOD SHOULD *NOT* BE CALLED.
     *
     * @note TickedTask Should not add to this builder
     */
    fun addLast(t: Task<R>, consumeTick: Int) {
        last = t to consumeTick
    }
}
