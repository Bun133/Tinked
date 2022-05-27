package com.github.bun133.tinked

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class EventTask<I, E : Event>(
    val predicate: (E) -> Boolean,
    val plugin: JavaPlugin,
    val eventClass: Class<E>,
    val priority: EventPriority = EventPriority.NORMAL,
    val ignoreCancelled: Boolean = true
) : TickedTask<I, E>(), Listener {
    override fun runnable(input: I): E {
        // This method is not reachable
        throw NotImplementedError()
    }

    override fun run(input: I) {
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

    private fun onEvent(event: E) {
        if (predicate(event)) {
            nextNode?.run(event)

            // Unregister Listener
            HandlerList.unregisterAll(this)
        }
    }
}