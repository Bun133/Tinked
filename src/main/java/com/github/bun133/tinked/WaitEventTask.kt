package com.github.bun133.tinked

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

/**
 * 条件に合うEventが発生するまで待機するタスク
 */
class WaitEventTask<I, E : Event>(
    val predicate: (E) -> Boolean,
    val plugin: JavaPlugin,
    val eventClass: Class<E>,
    val priority: EventPriority = EventPriority.NORMAL,
    val ignoreCancelled: Boolean = true
) : TickedTask<I, I>(), Listener {
    override fun runnable(input: I): I {
        // This method is not reachable
        throw NotImplementedError()
    }

    private var i: I? = null
    private var isSet = false

    override fun run(input: I) {
        i = input
        if (!isSet) {
            // Register Listener
            plugin.server.pluginManager.registerEvent(
                eventClass,
                this,
                priority,
                { _, event -> onEvent(event as E) },
                plugin,
                ignoreCancelled
            )

        }
        isSet = true
    }

    fun onEvent(e: E) {
        if (isSet && predicate(e)) {
            (i as I).let {
                nextNode?.run(it)
            }
        }
    }
}