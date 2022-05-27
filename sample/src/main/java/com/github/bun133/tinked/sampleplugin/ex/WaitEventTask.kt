package com.github.bun133.tinked.sampleplugin.ex

import com.github.bun133.tinked.RunnableTask
import dev.kotx.flylib.command.CommandContext
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent

class WaitEventTask : Example {
    override fun start(ctx: CommandContext) {
        val p = ctx.player
        if (p != null) {
            val task =
                com.github.bun133.tinked.WaitEventTask<Player, PlayerInteractEvent>(
                    { it.player == p },
                    ctx.plugin,
                    PlayerInteractEvent::class.java
                )
                    .apply {
                        then(RunnableTask { it.sendMessage(Component.text("Waited For PlayerInteractEvent!")) })
                    }

            task.run(p)
            ctx.success("Task Started")
        } else {
            ctx.fail("You are not a player")
        }
    }
}