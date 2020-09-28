package com.chattriggers.ctjs.mixin

import com.chattriggers.ctjs.utils.kotlin.MCITextComponent
import net.minecraft.client.gui.GuiPlayerTabOverlay
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(GuiPlayerTabOverlay::class)
interface GuiPlayerTabOverlayAccessor {
    @Accessor
    fun getHeader(): MCITextComponent

    @Accessor
    fun getFooter(): MCITextComponent
}
