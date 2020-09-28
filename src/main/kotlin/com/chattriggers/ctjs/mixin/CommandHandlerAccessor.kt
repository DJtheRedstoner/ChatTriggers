package com.chattriggers.ctjs.mixin

import net.minecraft.command.CommandHandler
import net.minecraft.command.ICommand
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(CommandHandler::class)
interface CommandHandlerAccessor {
    @Accessor
    fun getCommandMap(): MutableMap<String, ICommand>

    @Accessor
    fun getCommandSet(): MutableSet<ICommand>
}
