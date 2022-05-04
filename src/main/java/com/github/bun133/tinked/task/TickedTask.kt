package com.github.bun133.tinked.task

import com.github.bun133.tinked.utils.Future

/**
 * TickedTask is a task that is executed at exact tick duration.
 * and the processing of this task is done in that duration.
 * @param R the type of result of this task
 */
abstract class TickedTask<R : Any> : Task<R?> {
    private var currentTick = 0

    /**
     * This run method executes the task with incrementing the currentTick,
     *
     * @note This method should not be called directly.
     *
     * for auto incrementing currentTick, use [TickedTaskRunner]
     */
    override fun run(): R? {
        var toBe = currentTick + 1
        val c = consumeTick
        if (toBe > c) {
            toBe %= c
        }
        currentTick = toBe
        if (toBe == c) {
            // This is the last tick
            return run(currentTick, true)
        } else {
            run(currentTick, false)
            return null
        }
    }

    /**
     * Reset the currentTick to 0.
     */
    fun reset() {
        currentTick = 0
    }

    /**
     * This method is called when the task is executed.
     *
     * At The Last Tick, this method should return not null.
     *
     * @note This method should not be called directly.
     *
     * @param currentTick the current tick of the task.(*One*-Indexed)
     */
    abstract fun run(currentTick: Int, isLastTick: Boolean): R?

    /**
     * returns the length of this task in ticks.
     */
    abstract val consumeTick: Int

    /**
     * Cancel the running of this task.
     */
    abstract fun cancel()

    /**
     * Start the entire task.
     */
    abstract fun start(): Future<R>
}
