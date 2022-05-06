package com.github.bun133.tinked.task

import com.github.bun133.tinked.utils.Future
import org.bukkit.plugin.java.JavaPlugin

class TickedTaskRunnerImpl(override val plugin: JavaPlugin) : TickedTaskRunner(plugin) {
    private var isInit = false
    private val running = mutableListOf<>()

    override fun <I : Any, R : Any> run(i: I, task: TickedTask<I, R>): Future<R> {
        checkIf(task).also {

        }
    }

    override fun <I : Any, R : Any> cancel(task: TickedTask<I, R>) {

    }

    override fun <I : Any, R : Any> getTaskResult(task: TickedTask<I, R>): R? {
    }

    override fun runningTasks(): List<TickedTask<*, *>> {
    }

    private fun <I : Any, R : Any> checkIf(task: TickedTask<I, R>): TickedTaskImpl<I, R> {
        if (task is TickedTaskImpl<*, *>) {
            return task as TickedTaskImpl<I, R>
        } else {
            throw IllegalArgumentException("task is not TickedTaskImpl")
        }
    }
}