package com.chattriggers.ctjs.mixin

import net.minecraft.client.gui.GuiPlayerTabOverlay
import net.minecraft.util.IChatComponent
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(GuiPlayerTabOverlay::class)
interface GuiPlayerTabOverlayAccessor {
    @Accessor
    fun getHeader(): IChatComponent

    @Accessor
    fun getFooter(): IChatComponent
}
