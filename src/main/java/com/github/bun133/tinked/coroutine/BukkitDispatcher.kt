package com.github.bun133.tinked.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.plugin.java.JavaPlugin
import kotlin.coroutines.CoroutineContext

/**
 * BukkitのTickごとに実行するDispatcher
 */
class BukkitDispatcher private constructor(val plugin: JavaPlugin) : CoroutineDispatcher() {
    companion object {
        private var instance: BukkitDispatcher? = null
        fun getBukkitDispatcher(plugin: JavaPlugin): BukkitDispatcher {
            if (instance == null) {
                instance = BukkitDispatcher(plugin)
            }
            return instance!!
        }
    }


    private val list = mutableListOf<Runnable>()

    init {
        plugin.server.scheduler.runTaskTimer(plugin, Runnable {
            list.forEach { it.run() }
        }, 0, 1)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        list.add(block)
    }
}