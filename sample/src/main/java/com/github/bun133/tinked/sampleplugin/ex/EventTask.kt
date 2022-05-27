package com.github.bun133.tinked.sampleplugin.ex

import com.github.bun133.tinked.EventTask
import com.github.bun133.tinked.RunnableTask
import com.github.bun133.tinked.WaitTask
import dev.kotx.flylib.command.CommandContext
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class EventTask : Example {
    override fun start(ctx: CommandContext) {
        val p = ctx.player
        if (p != null) {
            val task = EventTask<Unit, PlayerInteractEvent>({
                it.player == p && it.action == Action.LEFT_CLICK_BLOCK
            }, ctx.plugin, PlayerInteractEvent::class.java)
                .apply {
                    then(WaitTask(1, ctx.plugin)).then(RunnableTask {
                        it.clickedBlock!!.type = Material.OBSIDIAN
                        it.player.sendMessage("PlayerInteractEvent!")
                    })
                }

            task.run(Unit)
        }
    }
}