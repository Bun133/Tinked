package com.github.bun133.tinked.task

import org.bukkit.plugin.java.JavaPlugin

/**
 * Runner of [TickedTask]
 */
abstract class TickedTaskRunner(open val plugin: JavaPlugin) {
    /**
     * These methods are should not be called manually
     */
    abstract fun <R : Any> run(task: TickedTask<R>)
    abstract fun <R : Any> cancel(task: TickedTask<R>)
    abstract fun <R : Any> getTaskResult(task: TickedTask<R>): R?
    abstract fun runningTasks(): List<TickedTask<*>>

    companion object {
        private val instances = mutableMapOf<JavaPlugin, TickedTaskRunner>()
        val defaultTaskRunnerGenerator: (JavaPlugin) -> TickedTaskRunner = { TickedTaskRunnerImpl(it) }

        fun instance(plugin: JavaPlugin): TickedTaskRunner {
            val g = instances[plugin]
            if (g != null) return g
            else {
                val e = defaultTaskRunnerGenerator(plugin)
                register(plugin, e)
                return e
            }
        }

        /**
         * For overriding this class,
         * child class must call this class to register itself.
         */
        fun register(plugin: JavaPlugin, instance: TickedTaskRunner): TickedTaskRunner {
            instances[plugin] = instance
            return instance
        }
    }
}