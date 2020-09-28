package com.chattriggers.ctjs.mixin

import net.minecraft.client.gui.ChatLine
import net.minecraft.client.gui.GuiNewChat
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(GuiNewChat::class)
interface GuiNewChatAccessor {
    @Accessor
    fun getChatLines(): List<ChatLine>

    @Accessor
    fun getDrawnChatLines(): List<ChatLine>
}
