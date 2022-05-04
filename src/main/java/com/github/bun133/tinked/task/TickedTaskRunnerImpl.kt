package com.github.bun133.tinked.task

import com.github.bun133.tinked.utils.MutablePair
import org.bukkit.plugin.java.JavaPlugin

class TickedTaskRunnerImpl(override val plugin: JavaPlugin) : TickedTaskRunner(plugin) {
    private var isInitialized = false
    private val runningTask = mutableListOf<MutablePair<Int, TickedTask<*>>>()
    private val results = mutableListOf<TickedTaskRunResult<*>>()

    override fun <R : Any> run(task: TickedTask<R>) {
        checkInitialized()
        runningTask.add(MutablePair(0, task))
    }

    override fun <R : Any> cancel(task: TickedTask<R>) {
        checkInitialized()
        runningTask.removeIf { it.value == task }
        submitResult(task, null)
    }

    override fun <R : Any> getTaskResult(task: TickedTask<R>): R? {
        checkInitialized()
        val r = results.firstOrNull { it.task == task }
        if (r != null) {
            return r.result as R?   // Checked cast
        } else {
            return null
        }
    }

    override fun runningTasks(): List<TickedTask<*>> = runningTask.map { it.value }.toList()

    private fun checkInitialized() {
        if (!isInitialized) {
            isInitialized = true
            plugin.server.scheduler.runTaskTimer(plugin, Runnable {
                tick()
            }, 0, 1)
        }
    }

    private fun tick() {
        doRuns()
    }

    private fun doRuns() {
        val toRemove = mutableListOf<MutablePair<Int, TickedTask<*>>>()
        runningTask.forEach { p ->
            if (p.key < p.value.consumeTick) {
                p.key += 1
                if (p.key == p.value.consumeTick) {
                    submitResult(p.value, p.value.run())
                } else {
                    p.value.run()
                }
            } else {
                // This task is finished
                toRemove.add(p)
            }
        }

        runningTask.removeAll(toRemove)
    }

    private fun <R : Any> submitResult(task: TickedTask<R>, result: Any?) {
        results.add(
            TickedTaskRunResult(
                task, result as R? // Checked cast
            )
        )
    }
}

data class TickedTaskRunResult<R : Any>(val task: TickedTask<R>, val result: R?)