package com.github.bun133.tinked

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * コンディションがtrueになるまで待機する
 */
class WaitUntilTask<I : Any>(private val plugin: JavaPlugin, var condition: (I) -> Boolean) : TickedTask<I, I>() {
    private var input: I? = null

    override fun run(input: I) {
        this.input = input
        tick()
    }

    override fun runnable(input: I): I {
        // This method is not reachable
        throw NotImplementedError()
    }

    private fun tick() {
        if (condition(input!!)) {
            nextNode?.run(input!!)
        } else {
            registerTimer()
        }
    }

    private fun registerTimer() {
        Bukkit.getServer().scheduler.runTaskTimer(plugin, this::tick, 0, 1)
    }
}