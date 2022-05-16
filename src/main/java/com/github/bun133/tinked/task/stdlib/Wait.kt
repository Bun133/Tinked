package com.github.bun133.tinked.task.stdlib

import com.github.bun133.tinked.task.Task
import org.bukkit.plugin.java.JavaPlugin


private class WaitCallBacker(val plugin: JavaPlugin) {
    companion object {
        private val instances = mutableMapOf<JavaPlugin, WaitCallBacker>()
        fun getInstance(plugin: JavaPlugin): WaitCallBacker {
            return instances.getOrPut(plugin) { WaitCallBacker(plugin) }
        }
    }

    init {
        instances[plugin] = this
    }

    private var isInit = false

    private fun init() {
        if (isInit) return
        plugin.server.scheduler.runTaskTimer(plugin, Runnable {
            tick()
        }, 0, 1)
        isInit = true
    }

    private val callBacks = mutableListOf<Pair<Int, () -> Unit>>()

    private fun tick() {
        val current = plugin.server.currentTick
        callBacks.removeIf { p ->
            if (p.first == current) {
                p.second()
                return@removeIf true
            } else {
                return@removeIf false
            }
        }
    }

    fun addWait(ticks: Int = 1, callBack: () -> Unit) {
        init()
        if (ticks > 0) {
            val to = plugin.server.currentTick + ticks
            callBacks.add(to to callBack)
        } else {
            throw IllegalArgumentException("ticks must be greater than 0")
        }
    }
}

fun <I : Any, R : Any> Task<I, R>.chainWait(plugin: JavaPlugin, ticks: Int, chainTo: Task<R, *>): Task<I, R> {
    val caller = WaitCallBacker.getInstance(plugin)

}