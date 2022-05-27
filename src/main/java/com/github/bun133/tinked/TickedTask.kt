package com.github.bun133.tinked

/**
 * The task which consumes ticks.
 * タスクが複数Tickを消費するもの
 */
abstract class TickedTask<I, T> : Task<I, T>() {
    override fun <V : Any> apply(other: Task<T, V>): TickedApplyedTask<I, T, V> {
        return TickedApplyedTask(this, other)
    }
}