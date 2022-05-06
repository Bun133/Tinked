package com.github.bun133.tinked.task

/**
 * Task class
 * This class should not have any state, and should be immutable
 *
 * @param I input type of the task
 * @param R return type of the task
 */
fun interface Task<I, R> {
    fun run(i: I): R
}