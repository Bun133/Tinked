package com.github.bun133.tinked

/**
 * The task which consumes ticks.
 * タスクが複数Tickを消費するもの
 */
abstract class TickedTask<I, T> : Task<I, T>() {
}