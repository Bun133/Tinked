package com.github.bun133.tinked

import org.bukkit.plugin.java.JavaPlugin

/**
 * 指定Tickタスクを繰り返し実行するタスク
 * @param block I:Input,Long:CurrentTick(0~ticks)
 * @param ticks 繰り返し回数
 */
class RepeatTask<I>(val ticks: Long, val plugin: JavaPlugin, val block: (I, Long) -> Unit) : TickedTask<I, I>() {
    override fun runnable(input: I): I {
        throw UnsupportedOperationException("Not supported yet.")
    }

    override fun run(input: I) {
        call(1, input)
    }

    private fun call(now: Long, i: I) {
        if (now > ticks) {
            nextNode?.run(i)
        } else {
            WaitTask<I>(1, plugin)
                .apply(RunnableTask {
                    block(it, now)
                    call(now + 1, i)
                }).run(i)
        }
    }
}