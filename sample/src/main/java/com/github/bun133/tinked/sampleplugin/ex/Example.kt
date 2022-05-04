package com.github.bun133.tinked.sampleplugin.ex

import dev.kotx.flylib.command.CommandContext

interface Example {
    fun start(ctx: CommandContext)
}