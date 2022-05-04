package com.github.bun133.tinked.task.builder

import com.github.bun133.tinked.task.Task

/**
 * Combine two tasks into one.
 */
operator fun <R : Any> Task<R>.plus(task: Task<R>): Task<R> {
    return Task {
        this.run()
        task.run()
    }
}