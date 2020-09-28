package com.chattriggers.ctjs.commands

import com.chattriggers.ctjs.triggers.OnTrigger
import com.chattriggers.ctjs.utils.kotlin.MCBlockPos
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

//#if MC>=11202
//$$ import net.minecraft.server.MinecraftServer
//#endif

class Command(
    trigger: OnTrigger,
    val commandString: String,
    private val usage: String,
    private val tabCompletionOptions: MutableList<String>
) : CommandBase() {
    val triggers = mutableListOf<OnTrigger>()

    init {
        this.triggers.add(trigger)
    }

    //#if MC>=11202
    //$$ override fun getName() = commandString
    //#else
    override fun getCommandName() = commandString
    //#endif

    override fun getRequiredPermissionLevel() = 0

    //#if MC>=11202
    //$$ override fun getUsage(sender: ICommandSender) = usage
    //#else
    override fun getCommandUsage(sender: ICommandSender) = usage
    //#endif

    //#if MC>=11202
    //$$ override fun getTabCompletions(
    //$$     server: MinecraftServer?,
    //$$     sender: ICommandSender?,
    //$$     args: Array<String?>?,
    //$$     targetPos: MCBlockPos?
    //$$ ): List<String?>? {
    //$$     return emptyList<String>()
    //$$ }
    //#else
    override fun addTabCompletionOptions(
        sender: ICommandSender?,
        args: Array<out String>?,
        pos: MCBlockPos?
    ): MutableList<String> {
        return tabCompletionOptions
    }
    //#endif

    @Throws(CommandException::class)
    //#if MC>=11202
    //$$ override fun execute(server: net.minecraft.server.MinecraftServer?, sender: ICommandSender, args: Array<String>) = trigger(args)
    //#else
    override fun processCommand(sender: ICommandSender, args: Array<String>) = trigger(args)
    //#endif

    private fun trigger(args: Array<String>) {
        triggers.forEach { it.trigger(args) }
    }
}
