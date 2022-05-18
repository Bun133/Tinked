package com.github.bun133.tinked.sampleplugin.ex

import com.github.bun133.tinked.RunnableTask
import com.github.bun133.tinked.WaitTask
import dev.kotx.flylib.command.CommandContext
import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin

/**
 * 3Tick待って実行するタスク
 */
class TickedTaskTest : Example {
    val testTask = { plugin: JavaPlugin ->
        RunnableTask<Unit, String> {
            "test"
        }.apply {
            then(WaitTask(3, plugin))
                .then(RunnableTask<String, Unit> { s ->
                    plugin.server.broadcast(Component.text(s))
                })
        }
    }

    override fun start(ctx: CommandContext) {
        testTask(ctx.plugin).run(Unit)
    }
}