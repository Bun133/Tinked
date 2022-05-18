package com.github.bun133.tinked.sampleplugin.ex

import com.github.bun133.tinked.RunnableTask
import com.github.bun133.tinked.Task
import dev.kotx.flylib.command.CommandContext
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class TaskTest : Example {
    val task = {
        PlayerListTask()
            .apply {
                then(MapPlayerNameTask())
                    .then(RunnableTask {
                        it.forEach { s ->
                            Bukkit.getServer().broadcast(Component.text(s))
                        }
                    })
            }
    }

    override fun start(ctx: CommandContext) {
        task().run(Unit)
    }
}

class PlayerListTask : Task<Unit, List<Player>>() {
    override fun runnable(input: Unit): List<Player> {
        return Bukkit.getServer().onlinePlayers.toList()
    }
}

class MapPlayerNameTask : Task<List<Player>, List<String>>() {
    override fun runnable(input: List<Player>): List<String> {
        return input.map { it.name }
    }
}