package com.github.bun133.tinked.task

import com.github.bun133.tinked.utils.MutableTriple
import org.bukkit.plugin.java.JavaPlugin

class TickedTaskRunnerImpl(override val plugin: JavaPlugin) : TickedTaskRunner(plugin) {
    private var isInitialized = false

    // CurrentTick , Task , TaskInput
    private val runningTask = mutableListOf<TaskEntry<*, *>>()
    private val results = mutableListOf<TickedTaskRunResult<*, *>>()

    override fun <I : Any, R : Any> run(i: I, task: TickedTask<I, R>, rClass: Class<R>) {
        checkInitialized()
        runningTask.add(TaskEntry(MutableTriple(0, task, i), rClass))
    }

    override fun <I : Any, R : Any> cancel(task: TickedTask<I, R>) {
        checkInitialized()
        runningTask.removeIf { it.en.value == task }
        submitResult(task, null)
    }

    override fun <I : Any, R : Any> getTaskResult(task: TickedTask<I, R>): R? {
        checkInitialized()
        val r = results.firstOrNull { it.task == task }
        if (r != null) {
            return r.result as? R   // Checked cast
        } else {
            return null
        }
    }

    override fun runningTasks(): List<TickedTask<*, *>> = runningTask.map { it.en.value }.toList()

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
        val toRemove = mutableListOf<TaskEntry<*, *>>()
        runningTask.forEach { p ->
            if (p.en.key < p.en.value.consumeTick) {
                p.en.key += 1
                if (p.en.key == p.en.value.consumeTick) {
                    submitResult(p.en.value, p.runTask())
                } else {
                    p.runTask()
                }
            } else {
                // This task is already finished
                toRemove.add(p)
            }
        }

        runningTask.removeAll(toRemove)
    }

    private fun <I : Any, R : Any> submitResult(task: TickedTask<I, R>, result: Any?) {
        results.add(
            TickedTaskRunResult(
                task, result as R? // Checked cast
            )
        )
    }
}

data class TickedTaskRunResult<I : Any, R : Any>(val task: TickedTask<I, R>, val result: R?)

private class TaskEntry<I : Any, R : Any>(
    val en: MutableTriple<Int, TickedTaskImpl<I, R>, Any?>,
    private val iC: Class<I>
) {
    fun runTask(): R? {
        val task = en.value.tasks[en.key]
    }

}