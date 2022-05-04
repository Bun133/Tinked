package com.github.bun133.tinked.sampleplugin.ex

import com.github.bun133.tinked.task.Task
import com.github.bun133.tinked.task.TickedTaskRunner
import com.github.bun133.tinked.task.builder.tickedTask
import dev.kotx.flylib.command.CommandContext
import org.bukkit.plugin.java.JavaPlugin

class TickedTaskEx(val plugin: JavaPlugin) : Example {
    override fun start(ctx: CommandContext) {
        val oneTask = Task {
            ctx.success("oneTask (This should be printed twice)")
        }

        val twoTask = Task {
            ctx.success("twoTask")
        }

        val runner = TickedTaskRunner.instance(plugin)

        val ticked = runner.tickedTask<Unit> {
            addNotLast(oneTask, 2)
            addLast(twoTask, 1)
        }

        ticked.start()
    }
}