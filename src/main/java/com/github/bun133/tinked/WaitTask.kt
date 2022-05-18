package com.github.bun133.tinked

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * 特定Tick待つタスク
 */
class WaitTask<I>(private val tick: Long, private val plugin: JavaPlugin) : TickedTask<I, I>() {
    override fun run(input: I) {
        Bukkit.getServer().scheduler.runTaskLater(plugin, Runnable {
            nextNode?.run(input)
        }, tick)
    }

    override fun runnable(input: I): I {
        // This method is not reachable
        throw NotImplementedError()
    }
}