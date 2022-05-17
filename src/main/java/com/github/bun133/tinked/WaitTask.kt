package com.github.bun133.tinked

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * 特定Tick待つタスク
 */
class WaitTask<I>(private val tick: Long, private val plugin: JavaPlugin) : RunnableTask<I, I>({ it }) {
    override fun run(input: I) {
        val result = runnable(input)
        Bukkit.getServer().scheduler.runTaskLater(plugin, Runnable {
            nextNode?.run(result)
        }, tick)
    }
}