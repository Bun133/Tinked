package com.github.bun133.tinked.task

import com.github.bun133.tinked.utils.Future

/**
 * TickedTask is a task that is executed at exact tick duration.
 * and the processing of this task is done in that duration.
 *
 * @param I the type of the input of this task
 * @param R the type of result of this task
 */
abstract class TickedTask<I : Any, R : Any> : Task<I, R?> {
    private var currentTick = 0

    /**
     * @note This method should not be called directly.
     *
     * @see [TickedTask.start]
     */
    override fun run(i: I): R {
        throw UnsupportedOperationException("This method should not be called directly.")
    }

    /**
     * Reset the currentTick to 0.
     */
    fun reset() {
        currentTick = 0
    }


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
    abstract fun start(i: I): Future<R>
}
