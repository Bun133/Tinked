package com.github.bun133.tinked.sampleplugin

import com.github.bun133.tinked.sampleplugin.ex.TaskEx
import com.github.bun133.tinked.sampleplugin.ex.TickedTaskEx
import dev.kotx.flylib.command.Command
import dev.kotx.flylib.flyLib
import org.bukkit.plugin.java.JavaPlugin

class Sampleplugin : JavaPlugin() {
    init {
        flyLib {
            command(SampleCommand())
        }
    }
}

class SampleCommand() : Command("tnk") {
    init {
        description("Sample Command of Tinked Library")
        usage {
            selectionArgument("arg", "Task", "TickedTask")
            executes {
                when (typedArgs[0] as String) {
                    "Task" -> {
                        success("Running Task")
                        TaskEx().start(this)
                    }
                    "TickedTask" -> {
                        success("Running TickedTask")
                        TickedTaskEx(this.plugin).start(this)
                    }
                    else -> {
                        fail("Unknown Task")
                    }
                }
            }
        }
    }
}