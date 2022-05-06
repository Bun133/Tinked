package com.github.bun133.tinked.action.builder

import com.github.bun133.tinked.action.ActionImpl

fun <I, R : Any> action(f: () -> Pair<TickedTask<I, R>, ((I) -> R?)?>): ActionImpl<I, R> {
    val (task, atOnce) = f()
    if (atOnce == null) {
        return ActionImpl(task) { null }
    } else {
        return ActionImpl<I, R>(task, atOnce)
    }
}