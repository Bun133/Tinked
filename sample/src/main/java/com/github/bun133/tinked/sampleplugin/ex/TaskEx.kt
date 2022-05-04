package com.github.bun133.tinked.sampleplugin.ex

import com.github.bun133.tinked.task.Task
import dev.kotx.flylib.command.CommandContext
import org.bukkit.Bukkit

class TaskEx : Example {
    val task = Task {
        Bukkit.getOnlinePlayers()
    }

    override fun start(ctx: CommandContext) {
        val returnValue = task.run()
        ctx.success("")
        ctx.success("return value: $returnValue")
    }
}