package com.github.bun133.tinked.action.builder

import com.github.bun133.tinked.action.ActionImpl
import com.github.bun133.tinked.task.TickedTask

fun <R : Any> action(f: () -> Pair<TickedTask<R>, (() -> R?)?>): ActionImpl<R> {
    val (task, atOnce) = f()
    if (atOnce == null) {
        return ActionImpl(task) { null }
    } else {
        return ActionImpl<R>(task, atOnce)
    }
}