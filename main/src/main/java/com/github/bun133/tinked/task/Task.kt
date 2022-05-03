package com.github.bun133.tinked.task

/**
 * Task class
 * This class should not have any state, and should be immutable
 * @param R return type of the task
 */
fun interface Task<R> {
    fun run(): R
}