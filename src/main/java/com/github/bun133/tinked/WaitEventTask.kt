package com.github.bun133.tinked

import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

/**
 * 条件に合うEventが発生するまで待機するタスク
 */
class WaitEventTask<I, E : Event>(val predicate: (E) -> Boolean, val plugin: JavaPlugin) : TickedTask<I, I>(),
    Listener {
    override fun runnable(input: I): I {
        // This method is not reachable
        throw NotImplementedError()
    }

    private var i: I? = null
    private var isSet = false

    override fun run(input: I) {
        i = input
        if (!isSet) plugin.server.pluginManager.registerEvents(this, plugin)
        isSet = true
    }

    @EventHandler
    fun onEvent(e: E) {
        if (isSet && predicate(e)) {
            (i as I).let {
                nextNode?.run(it)
            }
        }
    }
}